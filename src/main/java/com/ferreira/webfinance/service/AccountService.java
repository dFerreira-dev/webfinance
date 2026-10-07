package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.AccountRequestBody;
import com.ferreira.webfinance.entity.Account;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.AccountMapper;
import com.ferreira.webfinance.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public List<Account> findAll(){
        /*
         * Returns a list of all accounts
         */
        return accountRepository.findAll();
    }

    public Account findByIdOrThrowBadRequestException(long id) {
        /*
         * Returns an account if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return accountRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Account Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public Account save(AccountRequestBody accountRequestBody) {
        /*
         * save account
         * - This method expect a AccountRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new account
         * */
        return accountRepository.save(accountMapper.toAccount(accountRequestBody));
    }

    public void update(long id, AccountRequestBody accountRequestBody) {
        /*
         * update account
         * - This method firstly try to find an original account by id and returns it;
         * - If it's found, then a temporary account is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original account
         * - Finally, the repository saves the account updated
         * */

        Account account = findByIdOrThrowBadRequestException(id);

        Account updateData = accountMapper.toAccount(accountRequestBody);
        account.setAccountName(updateData.getAccountName());

        accountRepository.save(account);
    }

    public void delete(long id) {
        /*
         * Delete existing account using id
         * If id not exists, it throws a BadRequestExcepetion
         * */
        accountRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
