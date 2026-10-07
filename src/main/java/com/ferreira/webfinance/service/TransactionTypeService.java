package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.TransactionTypeRequestBody;
import com.ferreira.webfinance.entity.TransactionType;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.TransactionTypeMapper;
import com.ferreira.webfinance.repository.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionTypeMapper transactionTypeMapper;

    public List<TransactionType> findAll(){
        /*
         * Returns a list of all transaction types
         */
        return transactionTypeRepository.findAll();
    }

    public TransactionType findByIdOrThrowBadRequestException(long id) {
        /*
         * Returns a transaction type if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return transactionTypeRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transaction Nature Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public TransactionType save(TransactionTypeRequestBody transactionTypeRequestBody) {
        /*
         * save transaction type
         * - This method expect an TransactionTypeRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new transaction type
         * */
        return transactionTypeRepository.save(
                transactionTypeMapper.toTransactionType(transactionTypeRequestBody));
    }

    public void update(long id, TransactionTypeRequestBody transactionTypeRequestBody) {
        /*
         * update transaction type
         * - This method firstly try to find an original transaction type by id and returns it;
         * - If it's found, then a temporary transaction type is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original type
         * - Finally, the repository saves the transaction type
         * */
        TransactionType transactionType = findByIdOrThrowBadRequestException(id);

        TransactionType updatedData = transactionTypeMapper.toTransactionType(transactionTypeRequestBody);
        transactionType.setTransactionTypeName(updatedData.getTransactionTypeName());

        transactionTypeRepository.save(transactionType);
    }

    public void delete(long id) {

        /*
         * Delete existing transaction type using id
         * If id not exists, it throws a BadRequestExcepetion
         * */
        transactionTypeRepository.delete(findByIdOrThrowBadRequestException(id));
    }
}
