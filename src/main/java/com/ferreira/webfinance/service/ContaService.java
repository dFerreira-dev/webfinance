package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.ContaRequestBody;
import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.ContaMapper;
import com.ferreira.webfinance.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final ContaMapper contaMapper;

    public List<Conta> findAll(){
        /*
         * Returns a list of all accounts (contas)
         */
        return contaRepository.findAll();
    }

    public Conta findByIdOrThrowBadRequestException(long id) {
        /*
         * Returns an account (conta) if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return contaRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Account (Conta) Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public Conta save(ContaRequestBody contaRequestBody) {
        /*
         * save account (conta)
         * - This method expect a ContaRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new account
         * */
        return contaRepository.save(contaMapper.toConta(contaRequestBody));
    }

    // MUST IMPLEMENT POST REQUEST BODY
    public void update(long id, ContaRequestBody contaRequestBody) {
        /*
         * update account (conta)
         * - This method firstly try to find an original account by id and returns it;
         * - If it's found, then a temporary account is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original account
         * - Finally, the repository saves the account updated
         * */

        Conta conta = findByIdOrThrowBadRequestException(id);

        Conta updateData = contaMapper.toConta(contaRequestBody);
        conta.setNomeConta(updateData.getNomeConta());

        contaRepository.save(conta);
    }

    public void delete(long id) {
        /*
         * Delete existing account (conta) using id
         * If id not exists, it throws a BadRequestExcepetion
         * */
        contaRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
