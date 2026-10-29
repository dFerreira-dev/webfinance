package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.AccountRequestBody;
import com.ferreira.webfinance.entity.Account;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.AccountMapper;
import com.ferreira.webfinance.repository.AccountRepository;
import com.ferreira.webfinance.util.creator.entity.AccountCreator;
import com.ferreira.webfinance.util.creator.request.AccountRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepositoryMock;

    @Mock
    private AccountMapper accountMapperMock;

    //-------------------------------
    // findAll tests ----------------
    //-------------------------------
    @Test
    @DisplayName("findAll returns list of accounts when successful")
    void findAll_ReturnsListOfAccounts_WhenSuccessful() {
        Account account = AccountCreator.createValidAccount();

        BDDMockito.given(accountRepositoryMock.findAll())
                .willReturn(List.of(account));

        List<Account> accounts = accountService.findAll();

        Assertions.assertThat(accounts)
                .isNotNull()
                .hasSize(1)
                .contains(account);

        BDDMockito.then(accountRepositoryMock).should().findAll();
    }

    @Test
    @DisplayName("findAll returns empty list when no accounts are found")
    void findAll_ReturnsEmptyList_WhenNoAccountFound() {
        BDDMockito.given(accountRepositoryMock.findAll())
                .willReturn(Collections.emptyList());

        List<Account> accounts = accountService.findAll();

        Assertions.assertThat(accounts)
                .isNotNull()
                .isEmpty();

        BDDMockito.then(accountRepositoryMock).should().findAll();
    }

    //----------------------------------------------
    // findByIdOrThrowBadRequestException tests ----
    //----------------------------------------------
    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns account when successful")
    void findByIdOrThrowBadRequestException_ReturnsAccount_WhenSuccessful() {
        Account account = AccountCreator.createValidAccount();
        long expectedId = account.getAccountId();

        BDDMockito.given(accountRepositoryMock.findById(expectedId))
                .willReturn(Optional.of(account));

        Account accountFound = accountService.findByIdOrThrowBadRequestException(expectedId);

        Assertions.assertThat(accountFound).isNotNull().isEqualTo(account);
        Assertions.assertThat(accountFound.getAccountId()).isEqualTo(expectedId);

        BDDMockito.then(accountRepositoryMock).should().findById(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when id not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenIdNotFound() {
        long id = 1L;

        BDDMockito.given(accountRepositoryMock.findById(id))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> accountService.findByIdOrThrowBadRequestException(id))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(accountRepositoryMock).should().findById(ArgumentMatchers.anyLong());
    }

    //-------------------------------
    // save tests -------------------
    //-------------------------------
    @Test
    @DisplayName("save returns account when successful")
    void save_ReturnsAccount_WhenSuccessful() {
        AccountRequestBody accountRequestBody = AccountRequestBodyCreator.createAccountRequestBody();
        Account accountToBeSaved = AccountCreator.createAccountToBeSaved();
        Account accountSaved = AccountCreator.createValidAccount();

        BDDMockito.given(accountMapperMock.toAccount(accountRequestBody))
                .willReturn(accountToBeSaved);
        BDDMockito.given(accountRepositoryMock.save(accountToBeSaved))
                .willReturn(accountSaved);

        Account result = accountService.save(accountRequestBody);

        Assertions.assertThat(result).isNotNull().isEqualTo(accountSaved);

        BDDMockito.then(accountMapperMock).should().toAccount(accountRequestBody);
        BDDMockito.then(accountRepositoryMock).should().save(accountToBeSaved);
    }

    //-------------------------------
    // update tests -----------------
    //-------------------------------
    @Test
    @DisplayName("update replaces account when successful")
    void update_UpdatesAccount_WhenSuccessful() {
        long accountId = 1L;
        AccountRequestBody accountRequestBody = AccountRequestBodyCreator.createUpdatedAccountRequestBody();
        Account account = AccountCreator.createValidAccount();
        Account updateData = AccountCreator.createValidUpdatedAccount();

        BDDMockito.given(accountRepositoryMock.findById(accountId))
                .willReturn(Optional.of(account));
        BDDMockito.given(accountMapperMock.toAccount(accountRequestBody))
                .willReturn(updateData);

        accountService.update(accountId, accountRequestBody);

        Assertions.assertThat(account.getAccountName()).isEqualTo(updateData.getAccountName());

        BDDMockito.then(accountRepositoryMock).should().findById(accountId);
        BDDMockito.then(accountMapperMock).should().toAccount(accountRequestBody);
        BDDMockito.then(accountRepositoryMock).should().save(account);
    }

    @Test
    @DisplayName("update throws BadRequestException when account not found")
    void update_ThrowsBadRequestException_WhenAccountNotFound() {
        long accountId = 1L;
        AccountRequestBody accountRequestBody = AccountRequestBodyCreator.createAccountRequestBody();

        BDDMockito.given(accountRepositoryMock.findById(accountId))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> accountService.update(accountId, accountRequestBody))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(accountRepositoryMock)
                .should(BDDMockito.never())
                .save(ArgumentMatchers.any(Account.class));
        BDDMockito.then(accountMapperMock).shouldHaveNoInteractions();
    }

    //-------------------------------
    // delete tests -----------------
    //-------------------------------
    @Test
    @DisplayName("delete removes account when successful")
    void delete_RemovesAccount_WhenSuccessful() {
        long accountId = 1L;
        Account account = AccountCreator.createValidAccount();

        BDDMockito.given(accountRepositoryMock.findById(accountId))
                .willReturn(Optional.of(account));

        accountService.delete(accountId);

        BDDMockito.then(accountRepositoryMock).should().findById(accountId);
        BDDMockito.then(accountRepositoryMock).should().delete(account);
    }

    @Test
    @DisplayName("delete throws BadRequestException when account not found")
    void delete_ThrowsBadRequestException_WhenAccountNotFound() {
        long accountId = 1L;

        BDDMockito.given(accountRepositoryMock.findById(accountId))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> accountService.delete(accountId))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(accountRepositoryMock)
                .should(BDDMockito.never())
                .delete(ArgumentMatchers.any(Account.class));
    }
}