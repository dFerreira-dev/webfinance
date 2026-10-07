package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.TransactionNatureRequestBody;
import com.ferreira.webfinance.entity.TransactionNature;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.TransactionNatureMapper;
import com.ferreira.webfinance.repository.TransactionNatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionNatureService {
    private final TransactionNatureRepository transactionNatureRepository;
    private final TransactionNatureMapper transactionNatureMapper;

    public List<TransactionNature> findAll(){
        /*
         * Returns a list of all transactions natures
         */
        return transactionNatureRepository.findAll();
    }

    public TransactionNature findByIdOrThrowBadRequestException(long id) {
        /*
         * Returns a transaction nature if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return transactionNatureRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transaction Nature Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public TransactionNature save(TransactionNatureRequestBody transactionNatureRequestBody) {
        /*
         * save transaction nature
         * - This method expect an TransactionNatureRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new transaction nature
         * */
        return transactionNatureRepository.save(
                transactionNatureMapper.toTransactionNature(transactionNatureRequestBody));
    }

    public void update(long id, TransactionNatureRequestBody transactionNatureRequestBody) {
        /*
         * update transaction nature
         * - This method firstly try to find an original nature by id and returns it;
         * - If it's found, then a temporary nature is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original nature
         * - Finally, the repository saves the nature updated
         * */
        TransactionNature transactionNature = findByIdOrThrowBadRequestException(id);

        TransactionNature updatedData = transactionNatureMapper.toTransactionNature(transactionNatureRequestBody);
        transactionNature.setTransactionNatureName(updatedData.getTransactionNatureName());

        transactionNatureRepository.save(transactionNature);
    }

    public void delete(long id) {

        /*
         * Delete existing transaction nature using id
         * If id not exists, it throws a BadRequestExcepetion
         * */
        transactionNatureRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
