package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.TransactionRequestBody;
import com.ferreira.webfinance.entity.*;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.repository.*;
import com.ferreira.webfinance.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountRepository accountRepositoryMock;
    @Mock
    private PaymentMethodRepository paymentMethodRepositoryMock;
    @Mock
    private TransactionCategoryRepository transactionCategoryRepositoryMock;
    @Mock
    private TransactionNatureRepository transactionNatureRepositoryMock;
    @Mock
    private TransactionRepository transactionRepositoryMock;
    @Mock
    private TransactionTypeRepository transactionTypeRepositoryMock;

    //-------------------------------
    // util  ------------------------
    //-------------------------------
    private enum MissingDependency {
        ACCOUNT,
        TRANSACTION_TYPE,
        TRANSACTION_CATEGORY,
    }

    private static Stream<MissingDependency> missingDependencyProvider() {
        return Stream.of(MissingDependency.values());
    }

    //-------------------------------
    // findAll tests ----------------
    //-------------------------------
    @Test
    @DisplayName("findAll returns list of transactions when successful")
    void findAll_ReturnsListOfTransactions_WhenSuccessful() {
        Transaction transaction = TransactionCreator.createValidTransaction();

        BDDMockito.given(transactionRepositoryMock.findAll())
                .willReturn(List.of(transaction));

        List<Transaction> transactions = transactionService.findAll();

        Assertions.assertThat(transactions).isNotNull().hasSize(1).contains(transaction);

        BDDMockito.then(transactionRepositoryMock).should().findAll();
    }

    @Test
    @DisplayName("findAll returns empty list when no transactions are found")
    void findAll_ReturnsEmptyList_WhenNoTransactionFound() {

        BDDMockito.given(transactionRepositoryMock.findAll())
                .willReturn(Collections.emptyList());

        List<Transaction> transactions = transactionService.findAll();

        Assertions.assertThat(transactions).isNotNull().isEmpty();

        BDDMockito.then(transactionRepositoryMock).should().findAll();
    }


    //----------------------------------------------
    // findByIdOrThrowBadRequestException tests ----
    //----------------------------------------------
    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns transaction when successful")
    void findByIdOrThrowBadRequestException_ReturnsTransaction_WhenSuccessful() {
        Transaction transaction = TransactionCreator.createValidTransaction();
        long expectedId = transaction.getTransactionId();

        BDDMockito.given(transactionRepositoryMock.findById(expectedId))
                .willReturn(Optional.of(transaction));

        Transaction transactionFound = transactionService.findByIdOrThrowBadRequestException(expectedId);

        Assertions.assertThat(transactionFound).isNotNull().isEqualTo(transaction);
        Assertions.assertThat(transactionFound.getTransactionId()).isEqualTo(expectedId);

        BDDMockito.then(transactionRepositoryMock).should().findById(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when id not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenIdNotFound() {

        long id = 1L;

        BDDMockito.given(transactionRepositoryMock.findById(id))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionService.findByIdOrThrowBadRequestException(id))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionRepositoryMock).should().findById(ArgumentMatchers.anyLong());
    }

    //-------------------------------
    // save tests -------------------
    //-------------------------------
    @Test
    @DisplayName("save returns transaction when successful")
    void save_ReturnsTransaction_WhenSuccessful() {
        TransactionRequestBody transactionRequestBody = TransactionRequestBodyCreator.createTransactionRequestBody();

        Account account = AccountCreator.createValidAccount();
        PaymentMethod paymentMethod = PaymentMethodCreator.createValidPaymentMethod();
        TransactionCategory transactionCategory = TransactionCategoryCreator.createValidTransactionCategory();
        TransactionNature transactionNature = TransactionNatureCreator.createValidTransactionNature();
        TransactionType transactionType = TransactionTypeCreator.createValidTransactionType();
        Transaction transaction = TransactionCreator.createValidTransaction();

        BDDMockito.given(accountRepositoryMock.findById(transactionRequestBody.getAccountId()))
                .willReturn(Optional.of(account));

        BDDMockito.given(paymentMethodRepositoryMock.findById(transactionRequestBody.getPaymentMethodId()))
                .willReturn(Optional.of(paymentMethod));

        BDDMockito.given(transactionCategoryRepositoryMock.findById(transactionRequestBody.getTransactionCategoryId()))
                .willReturn(Optional.of(transactionCategory));

        BDDMockito.given(transactionNatureRepositoryMock.findById(transactionRequestBody.getTransactionNatureId()))
                .willReturn(Optional.of(transactionNature));

        BDDMockito.given(transactionTypeRepositoryMock.findById(transactionRequestBody.getTransactionTypeId()))
                .willReturn(Optional.of(transactionType));

        BDDMockito.given(transactionRepositoryMock.save(ArgumentMatchers.any(Transaction.class)))
                .willReturn(transaction);


        Transaction transactionSaved = transactionService.save(transactionRequestBody);

        Assertions.assertThat(transactionSaved).isNotNull().isEqualTo(transaction);
        BDDMockito.then(transactionRepositoryMock).should().save(ArgumentMatchers.any(Transaction.class));
    }

    @ParameterizedTest
    @MethodSource("missingDependencyProvider")
    @DisplayName("save throws BadRequestException when missing dependency")
    void save_ThrowsBadRequestException_WhenMissingDependency(MissingDependency missingDependency) {

        TransactionRequestBody transactionRequestBody = TransactionRequestBodyCreator.createTransactionRequestBody();

        switch (missingDependency) {
            case TRANSACTION_TYPE -> {
                BDDMockito.given(transactionTypeRepositoryMock.
                    findById(transactionRequestBody.getTransactionTypeId()))
                    .willReturn(Optional.empty());
            }

            case TRANSACTION_CATEGORY -> {
                BDDMockito.given(transactionTypeRepositoryMock.
                        findById(transactionRequestBody.getTransactionTypeId()))
                        .willReturn(Optional.of(TransactionTypeCreator.createValidTransactionType()));

                BDDMockito.given(transactionCategoryRepositoryMock
                    .findById(transactionRequestBody.getTransactionCategoryId()))
                    .willReturn(Optional.empty());

            }

            case ACCOUNT -> {

                BDDMockito.given(transactionTypeRepositoryMock.
                                findById(transactionRequestBody.getTransactionTypeId()))
                        .willReturn(Optional.of(TransactionTypeCreator.createValidTransactionType()));

                BDDMockito.given(transactionCategoryRepositoryMock
                                .findById(transactionRequestBody.getTransactionCategoryId()))
                        .willReturn(Optional.of(TransactionCategoryCreator.createValidTransactionCategory()));

                BDDMockito.given(transactionNatureRepositoryMock
                                .findById(transactionRequestBody.getTransactionNatureId()))
                        .willReturn(Optional.of(TransactionNatureCreator.createValidTransactionNature()));

                BDDMockito.given(paymentMethodRepositoryMock
                                .findById(transactionRequestBody.getPaymentMethodId()))
                        .willReturn(Optional.of(PaymentMethodCreator.createValidPaymentMethod()));

                BDDMockito.given(accountRepositoryMock.
                            findById(transactionRequestBody.getAccountId()))
                    .willReturn(Optional.empty());
            }

        }

        Assertions.assertThatThrownBy(() -> transactionService.save(transactionRequestBody))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionRepositoryMock).shouldHaveNoInteractions();
    }

    //-------------------------------
    // update tests -----------------
    //-------------------------------


    //-------------------------------
    // delete tests -----------------
    //-------------------------------
}