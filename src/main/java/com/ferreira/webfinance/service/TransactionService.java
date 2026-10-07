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
public class TransactionService {

    // service dependencies (repositories)
    private final TransactionCategoryRepository transactionCategoryRepository;
    private final AccountRepository accountRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final TransactionNatureRepository transactionNatureRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionRepository transactionRepository;

    //-------------------------------
    // PRIVATE/INTERNAL METHODS------
    //-------------------------------
    private Transaction buildTransaction(TransactionRequestBody transactionRequestBody) {

        //find transaction type
        TransactionType transactionType = transactionTypeRepository.findById(transactionRequestBody.getTransactionTypeId())
                .orElseThrow(()-> new BadRequestException("Transaction Type Not Found"));

        //find transaction category
        TransactionCategory transactionCategory = transactionRequestBody.getTransactionCategoryId() != null
                ? transactionCategoryRepository.findById(transactionRequestBody.getTransactionCategoryId())
                .orElseThrow(() -> new BadRequestException("Categoria Transaction Not Found")): null;

        //find transaction nature
        TransactionNature transactionNature = transactionNatureRepository.findById(transactionRequestBody.getNatureTransactionId())
                .orElseThrow(()-> new BadRequestException("Categoria Transaction Not Found"));

        //find payment method
        PaymentMethod paymentMethod = transactionRequestBody.getPaymentMethodId() != null
                ? paymentMethodRepository.findById(transactionRequestBody.getPaymentMethodId())
                .orElseThrow(()-> new BadRequestException("Meio Pagamento Not Found")) : null;

        //find account
        Account account = accountRepository.findById(transactionRequestBody.getAccountId())
                .orElseThrow(()-> new BadRequestException("Account Not Found"));

        //return Transaction built
        return Transaction.builder()
                .transactionDate(transactionRequestBody.getTransactionDate())
                .effectiveTransactionDate(transactionRequestBody.getEffectiveTransactionDate())
                .description(transactionRequestBody.getDescription())
                .transactionType(transactionType)
                .transactionCategory(transactionCategory)
                .transactionNature(transactionNature)
                .paymentMethod(paymentMethod)
                .account(account)
                .amount(transactionRequestBody.getAmount())
                .build();
    }

    //-------------------------------
    // CONTROLLER ACCESS METHODS-----
    //-------------------------------

    public List<Transaction> findAll(){
        /*
        * Returns a list of all transactions
        */
        return transactionRepository.findAll();
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public Transaction findByIdOrThrowBadRequestException(long id) {

        /*
        * Returns a transaction if the id is found
        * If isn't found, then throws a BadRequestExeption
        * */

        return transactionRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transaction Not Found"));
    }

    public void update(long id, TransactionRequestBody transactionRequestBody) {
        /*
         * update Transaction
         * - This method firstly try to find an original transaction by id en returns it;
         * - If it's found, then a temporary transaction is created using request data (updated data)
         * - Then, the data of updatedDate is copied to the original transaction
         * - Finally, the repository saves the transaction updated
         * */

        Transaction transaction = findByIdOrThrowBadRequestException(id);

        Transaction updatedData  = buildTransaction(transactionRequestBody);

        transaction.setTransactionDate(updatedData.getTransactionDate());
        transaction.setEffectiveTransactionDate(updatedData.getEffectiveTransactionDate());
        transaction.setDescription(updatedData.getDescription());
        transaction.setTransactionType(updatedData.getTransactionType());
        transaction.setTransactionCategory(updatedData.getTransactionCategory());
        transaction.setTransactionNature(updatedData.getTransactionNature());
        transaction.setPaymentMethod(updatedData.getPaymentMethod());
        transaction.setAccount(updatedData.getAccount());
        transaction.setAmount(updatedData.getAmount());
        transactionRepository.save(transaction);

    }

    public Transaction save(TransactionRequestBody transactionRequestBody) {
        /*
         * save transaction
        * - This method expect a TransactionRequestBody
        * - Then it calls buildTransaction passing the request
        * - buildTransaction returns the transaction
        * - repository saves new transaction
        * */

        return transactionRepository.save(buildTransaction(transactionRequestBody));
    }

    public void delete(long id) {
        /*
        * Delete existing transaction using id
        * If id not exists, it throws a BadRequestExcepetion
        * */
        transactionRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
