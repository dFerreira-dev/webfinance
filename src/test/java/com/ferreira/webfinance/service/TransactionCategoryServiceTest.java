package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.TransactionCategoryRequestBody;
import com.ferreira.webfinance.entity.TransactionCategory;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.TransactionCategoryMapper;
import com.ferreira.webfinance.repository.TransactionCategoryRepository;
import com.ferreira.webfinance.util.creator.entity.TransactionCategoryCreator;
import com.ferreira.webfinance.util.creator.request.TransactionCategoryRequestBodyCreator;
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
class TransactionCategoryServiceTest {

    @InjectMocks
    private TransactionCategoryService transactionCategoryService;

    @Mock
    private TransactionCategoryRepository transactionCategoryRepositoryMock;

    @Mock
    private TransactionCategoryMapper transactionCategoryMapperMock;

    //-------------------------------
    // findAll tests ----------------
    //-------------------------------
    @Test
    @DisplayName("findAll returns list of transaction categories when successful")
    void findAll_ReturnsListOfTransactionCategories_WhenSuccessful() {
        TransactionCategory transactionCategory = TransactionCategoryCreator.createValidTransactionCategory();

        BDDMockito.given(transactionCategoryRepositoryMock.findAll())
                .willReturn(List.of(transactionCategory));

        List<TransactionCategory> transactionCategories = transactionCategoryService.findAll();

        Assertions.assertThat(transactionCategories)
                .isNotNull()
                .hasSize(1)
                .contains(transactionCategory);

        BDDMockito.then(transactionCategoryRepositoryMock).should().findAll();
    }

    @Test
    @DisplayName("findAll returns empty list when no transaction categories are found")
    void findAll_ReturnsEmptyList_WhenNoTransactionCategoryFound() {
        BDDMockito.given(transactionCategoryRepositoryMock.findAll())
                .willReturn(Collections.emptyList());

        List<TransactionCategory> transactionCategories = transactionCategoryService.findAll();

        Assertions.assertThat(transactionCategories)
                .isNotNull()
                .isEmpty();

        BDDMockito.then(transactionCategoryRepositoryMock).should().findAll();
    }

    //----------------------------------------------
    // findByIdOrThrowBadRequestException tests ----
    //----------------------------------------------
    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns transaction category when successful")
    void findByIdOrThrowBadRequestException_ReturnsTransactionCategory_WhenSuccessful() {
        TransactionCategory transactionCategory = TransactionCategoryCreator.createValidTransactionCategory();
        long expectedId = transactionCategory.getTransactionCategoryId();

        BDDMockito.given(transactionCategoryRepositoryMock.findById(expectedId))
                .willReturn(Optional.of(transactionCategory));

        TransactionCategory transactionCategoryFound = transactionCategoryService.findByIdOrThrowBadRequestException(expectedId);

        Assertions.assertThat(transactionCategoryFound).isNotNull().isEqualTo(transactionCategory);
        Assertions.assertThat(transactionCategoryFound.getTransactionCategoryId()).isEqualTo(expectedId);

        BDDMockito.then(transactionCategoryRepositoryMock).should().findById(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when id not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenIdNotFound() {
        long id = 1L;

        BDDMockito.given(transactionCategoryRepositoryMock.findById(id))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionCategoryService.findByIdOrThrowBadRequestException(id))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionCategoryRepositoryMock).should().findById(ArgumentMatchers.anyLong());
    }

    //-------------------------------
    // save tests -------------------
    //-------------------------------
    @Test
    @DisplayName("save returns transaction category when successful")
    void save_ReturnsTransactionCategory_WhenSuccessful() {
        TransactionCategoryRequestBody transactionCategoryRequestBody = TransactionCategoryRequestBodyCreator.createTransactionCategoryRequestBody();
        TransactionCategory transactionCategoryToBeSaved = TransactionCategoryCreator.createTransactionCategoryToBeSaved();
        TransactionCategory transactionCategorySaved = TransactionCategoryCreator.createValidTransactionCategory();

        BDDMockito.given(transactionCategoryMapperMock.toTransactionCategory(transactionCategoryRequestBody))
                .willReturn(transactionCategoryToBeSaved);
        BDDMockito.given(transactionCategoryRepositoryMock.save(transactionCategoryToBeSaved))
                .willReturn(transactionCategorySaved);

        TransactionCategory result = transactionCategoryService.save(transactionCategoryRequestBody);

        Assertions.assertThat(result).isNotNull().isEqualTo(transactionCategorySaved);

        BDDMockito.then(transactionCategoryMapperMock).should().toTransactionCategory(transactionCategoryRequestBody);
        BDDMockito.then(transactionCategoryRepositoryMock).should().save(transactionCategoryToBeSaved);
    }

    //-------------------------------
    // update tests -----------------
    //-------------------------------
    @Test
    @DisplayName("update replaces transaction category when successful")
    void update_UpdatesTransactionCategory_WhenSuccessful() {
        long transactionCategoryId = 1L;
        TransactionCategoryRequestBody transactionCategoryRequestBody = TransactionCategoryRequestBodyCreator.createUpdatedTransactionCategoryRequestBody();
        TransactionCategory transactionCategory = TransactionCategoryCreator.createValidTransactionCategory();
        TransactionCategory updateData = TransactionCategoryCreator.createValidUpdatedTransactionCategory();

        BDDMockito.given(transactionCategoryRepositoryMock.findById(transactionCategoryId))
                .willReturn(Optional.of(transactionCategory));
        BDDMockito.given(transactionCategoryMapperMock.toTransactionCategory(transactionCategoryRequestBody))
                .willReturn(updateData);

        transactionCategoryService.update(transactionCategoryId, transactionCategoryRequestBody);

        Assertions.assertThat(transactionCategory.getTransactionCategoryName()).isEqualTo(updateData.getTransactionCategoryName());

        BDDMockito.then(transactionCategoryRepositoryMock).should().findById(transactionCategoryId);
        BDDMockito.then(transactionCategoryMapperMock).should().toTransactionCategory(transactionCategoryRequestBody);
        BDDMockito.then(transactionCategoryRepositoryMock).should().save(transactionCategory);
    }

    @Test
    @DisplayName("update throws BadRequestException when transaction category not found")
    void update_ThrowsBadRequestException_WhenTransactionCategoryNotFound() {
        long transactionCategoryId = 1L;
        TransactionCategoryRequestBody transactionCategoryRequestBody = TransactionCategoryRequestBodyCreator.createTransactionCategoryRequestBody();

        BDDMockito.given(transactionCategoryRepositoryMock.findById(transactionCategoryId))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionCategoryService.update(transactionCategoryId, transactionCategoryRequestBody))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionCategoryRepositoryMock)
                .should(BDDMockito.never())
                .save(ArgumentMatchers.any(TransactionCategory.class));
        BDDMockito.then(transactionCategoryMapperMock).shouldHaveNoInteractions();
    }

    //-------------------------------
    // delete tests -----------------
    //-------------------------------
    @Test
    @DisplayName("delete removes transaction category when successful")
    void delete_RemovesTransactionCategory_WhenSuccessful() {
        long transactionCategoryId = 1L;
        TransactionCategory transactionCategory = TransactionCategoryCreator.createValidTransactionCategory();

        BDDMockito.given(transactionCategoryRepositoryMock.findById(transactionCategoryId))
                .willReturn(Optional.of(transactionCategory));

        transactionCategoryService.delete(transactionCategoryId);

        BDDMockito.then(transactionCategoryRepositoryMock).should().findById(transactionCategoryId);
        BDDMockito.then(transactionCategoryRepositoryMock).should().delete(transactionCategory);
    }

    @Test
    @DisplayName("delete throws BadRequestException when transaction category not found")
    void delete_ThrowsBadRequestException_WhenTransactionCategoryNotFound() {
        long transactionCategoryId = 1L;

        BDDMockito.given(transactionCategoryRepositoryMock.findById(transactionCategoryId))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionCategoryService.delete(transactionCategoryId))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionCategoryRepositoryMock)
                .should(BDDMockito.never())
                .delete(ArgumentMatchers.any(TransactionCategory.class));
    }
}
