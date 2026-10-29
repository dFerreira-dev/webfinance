package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.TransactionNatureRequestBody;
import com.ferreira.webfinance.entity.TransactionNature;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.TransactionNatureMapper;
import com.ferreira.webfinance.repository.TransactionNatureRepository;
import com.ferreira.webfinance.util.creator.entity.TransactionNatureCreator;
import com.ferreira.webfinance.util.creator.request.TransactionNatureRequestBodyCreator;
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
class TransactionNatureServiceTest {

    @InjectMocks
    private TransactionNatureService transactionNatureService;

    @Mock
    private TransactionNatureRepository transactionNatureRepositoryMock;

    @Mock
    private TransactionNatureMapper transactionNatureMapperMock;

    //-------------------------------
    // findAll tests ----------------
    //-------------------------------
    @Test
    @DisplayName("findAll returns list of transaction natures when successful")
    void findAll_ReturnsListOfTransactionNatures_WhenSuccessful() {
        TransactionNature transactionNature = TransactionNatureCreator.createValidTransactionNature();

        BDDMockito.given(transactionNatureRepositoryMock.findAll())
                .willReturn(List.of(transactionNature));

        List<TransactionNature> transactionNatures = transactionNatureService.findAll();

        Assertions.assertThat(transactionNatures)
                .isNotNull()
                .hasSize(1)
                .contains(transactionNature);

        BDDMockito.then(transactionNatureRepositoryMock).should().findAll();
    }

    @Test
    @DisplayName("findAll returns empty list when no transaction natures are found")
    void findAll_ReturnsEmptyList_WhenNoTransactionNatureFound() {
        BDDMockito.given(transactionNatureRepositoryMock.findAll())
                .willReturn(Collections.emptyList());

        List<TransactionNature> transactionNatures = transactionNatureService.findAll();

        Assertions.assertThat(transactionNatures)
                .isNotNull()
                .isEmpty();

        BDDMockito.then(transactionNatureRepositoryMock).should().findAll();
    }

    //----------------------------------------------
    // findByIdOrThrowBadRequestException tests ----
    //----------------------------------------------
    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns transaction nature when successful")
    void findByIdOrThrowBadRequestException_ReturnsTransactionNature_WhenSuccessful() {
        TransactionNature transactionNature = TransactionNatureCreator.createValidTransactionNature();
        long expectedId = transactionNature.getTransactionNatureId();

        BDDMockito.given(transactionNatureRepositoryMock.findById(expectedId))
                .willReturn(Optional.of(transactionNature));

        TransactionNature transactionNatureFound = transactionNatureService.findByIdOrThrowBadRequestException(expectedId);

        Assertions.assertThat(transactionNatureFound).isNotNull().isEqualTo(transactionNature);
        Assertions.assertThat(transactionNatureFound.getTransactionNatureId()).isEqualTo(expectedId);

        BDDMockito.then(transactionNatureRepositoryMock).should().findById(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when id not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenIdNotFound() {
        long id = 1L;

        BDDMockito.given(transactionNatureRepositoryMock.findById(id))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionNatureService.findByIdOrThrowBadRequestException(id))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionNatureRepositoryMock).should().findById(ArgumentMatchers.anyLong());
    }

    //-------------------------------
    // save tests -------------------
    //-------------------------------
    @Test
    @DisplayName("save returns transaction nature when successful")
    void save_ReturnsTransactionNature_WhenSuccessful() {
        TransactionNatureRequestBody transactionNatureRequestBody = TransactionNatureRequestBodyCreator.createTransactionNatureRequestBody();
        TransactionNature transactionNatureToBeSaved = TransactionNatureCreator.createTransactionNatureToBeSaved();
        TransactionNature transactionNatureSaved = TransactionNatureCreator.createValidTransactionNature();

        BDDMockito.given(transactionNatureMapperMock.toTransactionNature(transactionNatureRequestBody))
                .willReturn(transactionNatureToBeSaved);
        BDDMockito.given(transactionNatureRepositoryMock.save(transactionNatureToBeSaved))
                .willReturn(transactionNatureSaved);

        TransactionNature result = transactionNatureService.save(transactionNatureRequestBody);

        Assertions.assertThat(result).isNotNull().isEqualTo(transactionNatureSaved);

        BDDMockito.then(transactionNatureMapperMock).should().toTransactionNature(transactionNatureRequestBody);
        BDDMockito.then(transactionNatureRepositoryMock).should().save(transactionNatureToBeSaved);
    }

    //-------------------------------
    // update tests -----------------
    //-------------------------------
    @Test
    @DisplayName("update replaces transaction nature when successful")
    void update_UpdatesTransactionNature_WhenSuccessful() {
        long transactionNatureId = 1L;
        TransactionNatureRequestBody transactionNatureRequestBody = TransactionNatureRequestBodyCreator.createUpdatedTransactionNatureRequestBody();
        TransactionNature transactionNature = TransactionNatureCreator.createValidTransactionNature();
        TransactionNature updateData = TransactionNatureCreator.createValidUpdatedTransactionNature();

        BDDMockito.given(transactionNatureRepositoryMock.findById(transactionNatureId))
                .willReturn(Optional.of(transactionNature));
        BDDMockito.given(transactionNatureMapperMock.toTransactionNature(transactionNatureRequestBody))
                .willReturn(updateData);

        transactionNatureService.update(transactionNatureId, transactionNatureRequestBody);

        Assertions.assertThat(transactionNature.getTransactionNatureName()).isEqualTo(updateData.getTransactionNatureName());

        BDDMockito.then(transactionNatureRepositoryMock).should().findById(transactionNatureId);
        BDDMockito.then(transactionNatureMapperMock).should().toTransactionNature(transactionNatureRequestBody);
        BDDMockito.then(transactionNatureRepositoryMock).should().save(transactionNature);
    }

    @Test
    @DisplayName("update throws BadRequestException when transaction nature not found")
    void update_ThrowsBadRequestException_WhenTransactionNatureNotFound() {
        long transactionNatureId = 1L;
        TransactionNatureRequestBody transactionNatureRequestBody = TransactionNatureRequestBodyCreator.createTransactionNatureRequestBody();

        BDDMockito.given(transactionNatureRepositoryMock.findById(transactionNatureId))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionNatureService.update(transactionNatureId, transactionNatureRequestBody))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionNatureRepositoryMock)
                .should(BDDMockito.never())
                .save(ArgumentMatchers.any(TransactionNature.class));
        BDDMockito.then(transactionNatureMapperMock).shouldHaveNoInteractions();
    }

    //-------------------------------
    // delete tests -----------------
    //-------------------------------
    @Test
    @DisplayName("delete removes transaction nature when successful")
    void delete_RemovesTransactionNature_WhenSuccessful() {
        long transactionNatureId = 1L;
        TransactionNature transactionNature = TransactionNatureCreator.createValidTransactionNature();

        BDDMockito.given(transactionNatureRepositoryMock.findById(transactionNatureId))
                .willReturn(Optional.of(transactionNature));

        transactionNatureService.delete(transactionNatureId);

        BDDMockito.then(transactionNatureRepositoryMock).should().findById(transactionNatureId);
        BDDMockito.then(transactionNatureRepositoryMock).should().delete(transactionNature);
    }

    @Test
    @DisplayName("delete throws BadRequestException when transaction nature not found")
    void delete_ThrowsBadRequestException_WhenTransactionNatureNotFound() {
        long transactionNatureId = 1L;

        BDDMockito.given(transactionNatureRepositoryMock.findById(transactionNatureId))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionNatureService.delete(transactionNatureId))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(transactionNatureRepositoryMock)
                .should(BDDMockito.never())
                .delete(ArgumentMatchers.any(TransactionNature.class));
    }
}
