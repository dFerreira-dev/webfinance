package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.TransactionTypeRequestBody;
import com.ferreira.webfinance.entity.TransactionType;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.TransactionTypeMapper;
import com.ferreira.webfinance.repository.TransactionTypeRepository;
import com.ferreira.webfinance.util.creator.entity.TransactionTypeCreator;
import com.ferreira.webfinance.util.creator.request.TransactionTypeRequestBodyCreator;
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
class TransactionTypeServiceTest {

    @InjectMocks
    private TransactionTypeService transactionTypeService;

    @Mock
    private TransactionTypeRepository transactionTypeRepositoryMock;

    @Mock
    private TransactionTypeMapper transactionTypeMapperMock;

    //-------------------------------
    // findAll tests ----------------
    //-------------------------------
    @Test
    @DisplayName("findAll returns list of transaction types when successful")
    void findAll_ReturnsListOfTransactionTypes_WhenSuccessful() {
        TransactionType transactionType = TransactionTypeCreator.createValidTransactionType();

        BDDMockito.given(transactionTypeRepositoryMock.findAll())
                .willReturn(List.of(transactionType));

        List<TransactionType> transactionTypes = transactionTypeService.findAll();

        Assertions.assertThat(transactionTypes)
                .isNotNull()
                .hasSize(1)
                .contains(transactionType);

        BDDMockito.then(transactionTypeRepositoryMock).should().findAll();
    }

    @Test
    @DisplayName("findAll returns empty list when no transaction types are found")
    void findAll_ReturnsEmptyList_WhenNoTransactionTypeFound() {
        BDDMockito.given(transactionTypeRepositoryMock.findAll())
                .willReturn(Collections.emptyList());

        List<TransactionType> transactionTypes = transactionTypeService.findAll();

        Assertions.assertThat(transactionTypes)
                .isNotNull()
                .isEmpty();

        BDDMockito.then(transactionTypeRepositoryMock).should().findAll();
    }

    //----------------------------------------------
    // findByIdOrThrowBadRequestException tests ----
    //----------------------------------------------
    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns transaction type when successful")
    void findByIdOrThrowBadRequestException_ReturnsTransactionType_WhenSuccessful() {
        TransactionType transactionType = TransactionTypeCreator.createValidTransactionType();
        long expectedId = transactionType.getTransactionTypeId();

        BDDMockito.given(transactionTypeRepositoryMock.findById(expectedId))
                .willReturn(Optional.of(transactionType));

        TransactionType transactionTypeFound = transactionTypeService.findByIdOrThrowBadRequestException(expectedId);

        Assertions.assertThat(transactionTypeFound).isNotNull().isEqualTo(transactionType);
        Assertions.assertThat(transactionTypeFound.getTransactionTypeId()).isEqualTo(expectedId);

        BDDMockito.then(transactionTypeRepositoryMock).should().findById(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when id not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenIdNotFound() {
        long id = 1L;

        BDDMockito.given(transactionTypeRepositoryMock.findById(id))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionTypeService.findByIdOrThrowBadRequestException(id))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionTypeRepositoryMock).should().findById(ArgumentMatchers.anyLong());
    }

    //-------------------------------
    // save tests -------------------
    //-------------------------------
    @Test
    @DisplayName("save returns transaction type when successful")
    void save_ReturnsTransactionType_WhenSuccessful() {
        TransactionTypeRequestBody transactionTypeRequestBody = TransactionTypeRequestBodyCreator.createTransactionTypeRequestBody();
        TransactionType transactionTypeToBeSaved = TransactionTypeCreator.createTransactionTypeToBeSaved();
        TransactionType transactionTypeSaved = TransactionTypeCreator.createValidTransactionType();

        BDDMockito.given(transactionTypeMapperMock.toTransactionType(transactionTypeRequestBody))
                .willReturn(transactionTypeToBeSaved);
        BDDMockito.given(transactionTypeRepositoryMock.save(transactionTypeToBeSaved))
                .willReturn(transactionTypeSaved);

        TransactionType result = transactionTypeService.save(transactionTypeRequestBody);

        Assertions.assertThat(result).isNotNull().isEqualTo(transactionTypeSaved);

        BDDMockito.then(transactionTypeMapperMock).should().toTransactionType(transactionTypeRequestBody);
        BDDMockito.then(transactionTypeRepositoryMock).should().save(transactionTypeToBeSaved);
    }

    //-------------------------------
    // update tests -----------------
    //-------------------------------
    @Test
    @DisplayName("update replaces transaction type when successful")
    void update_UpdatesTransactionType_WhenSuccessful() {
        long transactionTypeId = 1L;
        TransactionTypeRequestBody transactionTypeRequestBody = TransactionTypeRequestBodyCreator.createUpdatedTransactionTypeRequestBody();
        TransactionType transactionType = TransactionTypeCreator.createValidTransactionType();
        TransactionType updateData = TransactionTypeCreator.createValidUpdatedTransactionType();

        BDDMockito.given(transactionTypeRepositoryMock.findById(transactionTypeId))
                .willReturn(Optional.of(transactionType));
        BDDMockito.given(transactionTypeMapperMock.toTransactionType(transactionTypeRequestBody))
                .willReturn(updateData);

        transactionTypeService.update(transactionTypeId, transactionTypeRequestBody);

        Assertions.assertThat(transactionType.getTransactionTypeName()).isEqualTo(updateData.getTransactionTypeName());

        BDDMockito.then(transactionTypeRepositoryMock).should().findById(transactionTypeId);
        BDDMockito.then(transactionTypeMapperMock).should().toTransactionType(transactionTypeRequestBody);
        BDDMockito.then(transactionTypeRepositoryMock).should().save(transactionType);
    }

    @Test
    @DisplayName("update throws BadRequestException when transaction type not found")
    void update_ThrowsBadRequestException_WhenTransactionTypeNotFound() {
        long transactionTypeId = 1L;
        TransactionTypeRequestBody transactionTypeRequestBody = TransactionTypeRequestBodyCreator.createTransactionTypeRequestBody();

        BDDMockito.given(transactionTypeRepositoryMock.findById(transactionTypeId))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionTypeService.update(transactionTypeId, transactionTypeRequestBody))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionTypeRepositoryMock)
                .should(BDDMockito.never())
                .save(ArgumentMatchers.any(TransactionType.class));
        BDDMockito.then(transactionTypeMapperMock).shouldHaveNoInteractions();
    }

    //-------------------------------
    // delete tests -----------------
    //-------------------------------
    @Test
    @DisplayName("delete removes transaction type when successful")
    void delete_RemovesTransactionType_WhenSuccessful() {
        long transactionTypeId = 1L;
        TransactionType transactionType = TransactionTypeCreator.createValidTransactionType();

        BDDMockito.given(transactionTypeRepositoryMock.findById(transactionTypeId))
                .willReturn(Optional.of(transactionType));

        transactionTypeService.delete(transactionTypeId);

        BDDMockito.then(transactionTypeRepositoryMock).should().findById(transactionTypeId);
        BDDMockito.then(transactionTypeRepositoryMock).should().delete(transactionType);
    }

    @Test
    @DisplayName("delete throws BadRequestException when transaction type not found")
    void delete_ThrowsBadRequestException_WhenTransactionTypeNotFound() {
        long transactionTypeId = 1L;

        BDDMockito.given(transactionTypeRepositoryMock.findById(transactionTypeId))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionTypeService.delete(transactionTypeId))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionTypeRepositoryMock)
                .should(BDDMockito.never())
                .delete(ArgumentMatchers.any(TransactionType.class));
    }
}
