package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.TransactionRequestBody;
import com.ferreira.webfinance.entity.*;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransactionCategoryRepository transactionCategoryRepository;
    private final AccountRepository accountRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final TransactionNatureRepository transactionNatureRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionRepository transactionRepository;

    //-------------------------------
    // PRIVATE/INTERNAL METHODS------
    //-------------------------------
    private Transaction buildTransacao(TransactionRequestBody transactionRequestBody) {

        //find Tipo Transaction
        TransactionType transactionType = transactionTypeRepository.findById(transactionRequestBody.getTipoTransacaoId())
                .orElseThrow(()-> new BadRequestException("Tipo Transaction Not Found"));

        //find Categoria Transaction
        TransactionCategory transactionCategory = transactionRequestBody.getCategoriaTransacaoId() != null
                ? transactionCategoryRepository.findById(transactionRequestBody.getCategoriaTransacaoId())
                .orElseThrow(() -> new BadRequestException("Categoria Transaction Not Found")): null;

        //find Natureza Transaction
        TransactionNature transactionNature = transactionNatureRepository.findById(transactionRequestBody.getNaturezaTransacaoId())
                .orElseThrow(()-> new BadRequestException("Categoria Transaction Not Found"));

        //find Meio Pagamento
        PaymentMethod paymentMethod = transactionRequestBody.getMeioPagamentoId() != null
                ? paymentMethodRepository.findById(transactionRequestBody.getMeioPagamentoId())
                .orElseThrow(()-> new BadRequestException("Meio Pagamento Not Found")) : null;

        //find Account
        Account account = accountRepository.findById(transactionRequestBody.getContaId())
                .orElseThrow(()-> new BadRequestException("Account Not Found"));

        //return Transaction built
        return Transaction.builder()
                .dataTransacao(transactionRequestBody.getDataTransacao())
                .dataEfetivaPagamento(transactionRequestBody.getDataEfetivaPagamento())
                .descricao(transactionRequestBody.getDescricao())
                .transactionType(transactionType)
                .transactionCategory(transactionCategory)
                .naturezaTransacao(transactionNature)
                .paymentMethod(paymentMethod)
                .account(account)
                .valor(transactionRequestBody.getValor())
                .build();
    }

    //-------------------------------
    // CONTROLLER ACCESS METHODS-----
    //-------------------------------

    public List<Transaction> findAll(){
        /*
        * Returns a list of all transacoes (transactions)
        */
        return transactionRepository.findAll();
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public Transaction findByIdOrThrowBadRequestException(long id) {

        /*
        * Returns a Transaction if the id is found
        * If isn't found, then returns a BadRequestExeption
        * */

        return transactionRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transaction Not Found"));
    }

    public void update(long id, TransactionRequestBody transactionRequestBody) {
        /*update Transaction
         * - This method firstly try to find an original Transaction by id en returns it;
         * - If it's found, then a temporary Transaction is created using request data (updated data)
         * - Then, the data of updatedDate is copied to the original Transaction
         * - Finally, the repository saves the Transaction updated
         * */

        Transaction transaction = findByIdOrThrowBadRequestException(id);

        Transaction updatedData  = buildTransacao(transactionRequestBody);

        transaction.setDataTransacao(updatedData.getDataTransacao());
        transaction.setDataEfetivaPagamento(updatedData.getDataEfetivaPagamento());
        transaction.setDescricao(updatedData.getDescricao());
        transaction.setTransactionType(updatedData.getTransactionType());
        transaction.setTransactionCategory(updatedData.getTransactionCategory());
        transaction.setTransactionNature(updatedData.getTransactionNature());
        transaction.setPaymentMethod(updatedData.getPaymentMethod());
        transaction.setAccount(updatedData.getAccount());
        transaction.setAmount(updatedData.getAmount());
        transactionRepository.save(transaction);

    }

    public Transaction save(TransactionRequestBody transactionRequestBody) {
        /*save Transaction
        * - This methods expect a TransacaoRequestBody
        * - Then it calls buildTransacao passing the request
        * - buildTransacao returns the Transaction
        * - repository saves new Transaction
        * */

        return transactionRepository.save(buildTransacao(transactionRequestBody));
    }

    public void delete(long id) {
        /*
        * Delete existing transacao (transaction) using id
        * If id not exists, it returns a BadRequestExcepetion
        * */
        transactionRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
