package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.TransactionCategoryRequestBody;
import com.ferreira.webfinance.entity.TransactionCategory;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.TransactionCategoryMapper;
import com.ferreira.webfinance.repository.TransactionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaTransacaoService {

    private final TransactionCategoryRepository transactionCategoryRepository;
    private final TransactionCategoryMapper transactionCategoryMapper;

    public List<TransactionCategory> findAll(){
        /*
         * Returns a list of all categories (Categoria Transaction)
         */
        return transactionCategoryRepository.findAll();
    }

    public TransactionCategory findByIdOrThrowBadRequestException(long id) {
        /*
         * Returns a transaction category (categoria transacao) if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return transactionCategoryRepository.findById(id).
                orElseThrow(() -> new BadRequestException("TransactionCategory Not Found"));
    }

    public TransactionCategory save(TransactionCategoryRequestBody transactionCategoryRequestBody) {
        /*
         * save transaction category (categoria transacao)
         * - This method expect a TransactionCategoryRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new TransactionCategory
         * */
        return transactionCategoryRepository.save(
                transactionCategoryMapper
                .toTransactionCategory(transactionCategoryRequestBody));
    }


    public void update(long id, TransactionCategoryRequestBody transactionCategoryRequestBody) {

        /*
         *update transaction category (categoria transacao)
         * - This method firstly try to find an original category by id and returns it;
         * - If it's found, then a temporary category is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original category
         * - Finally, the repository saves the category updated
         * */

        TransactionCategory transactionCategory = findByIdOrThrowBadRequestException(id);

        TransactionCategory updatedData  = transactionCategoryMapper.toTransactionCategory(transactionCategoryRequestBody);
        transactionCategory.setTransactionCategoryName(updatedData.getTransactionCategoryName());

        transactionCategoryRepository.save(transactionCategory);
    }

    public void delete(long id) {
        /*
         * Delete existing transaction category (categoria transacao) using id
         * If id not exists, it throws a BadRequestExcepetion
         * */
        transactionCategoryRepository.delete(findByIdOrThrowBadRequestException(id));
    }


}
