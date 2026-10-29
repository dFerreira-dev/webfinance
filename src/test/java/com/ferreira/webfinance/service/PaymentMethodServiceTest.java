package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.PaymentMethodRequestBody;
import com.ferreira.webfinance.entity.PaymentMethod;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.PaymentMethodMapper;
import com.ferreira.webfinance.repository.PaymentMethodRepository;
import com.ferreira.webfinance.util.creator.entity.PaymentMethodCreator;
import com.ferreira.webfinance.util.creator.request.PaymentMethodRequestBodyCreator;
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
class PaymentMethodServiceTest {

    @InjectMocks
    private PaymentMethodService paymentMethodService;

    @Mock
    private PaymentMethodRepository paymentMethodRepositoryMock;

    @Mock
    private PaymentMethodMapper paymentMethodMapperMock;

    //-------------------------------
    // findAll tests ----------------
    //-------------------------------
    @Test
    @DisplayName("findAll returns list of payment methods when successful")
    void findAll_ReturnsListOfPaymentMethods_WhenSuccessful() {
        PaymentMethod paymentMethod = PaymentMethodCreator.createValidPaymentMethod();

        BDDMockito.given(paymentMethodRepositoryMock.findAll())
                .willReturn(List.of(paymentMethod));

        List<PaymentMethod> paymentMethods = paymentMethodService.findAll();

        Assertions.assertThat(paymentMethods)
                .isNotNull()
                .hasSize(1)
                .contains(paymentMethod);

        BDDMockito.then(paymentMethodRepositoryMock).should().findAll();
    }

    @Test
    @DisplayName("findAll returns empty list when no payment methods are found")
    void findAll_ReturnsEmptyList_WhenNoPaymentMethodFound() {
        BDDMockito.given(paymentMethodRepositoryMock.findAll())
                .willReturn(Collections.emptyList());

        List<PaymentMethod> paymentMethods = paymentMethodService.findAll();

        Assertions.assertThat(paymentMethods)
                .isNotNull()
                .isEmpty();

        BDDMockito.then(paymentMethodRepositoryMock).should().findAll();
    }

    //----------------------------------------------
    // findByIdOrThrowBadRequestException tests ----
    //----------------------------------------------
    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns payment method when successful")
    void findByIdOrThrowBadRequestException_ReturnsPaymentMethod_WhenSuccessful() {
        PaymentMethod paymentMethod = PaymentMethodCreator.createValidPaymentMethod();
        long expectedId = paymentMethod.getPaymentMethodID();

        BDDMockito.given(paymentMethodRepositoryMock.findById(expectedId))
                .willReturn(Optional.of(paymentMethod));

        PaymentMethod paymentMethodFound = paymentMethodService.findByIdOrThrowBadRequestException(expectedId);

        Assertions.assertThat(paymentMethodFound).isNotNull().isEqualTo(paymentMethod);
        Assertions.assertThat(paymentMethodFound.getPaymentMethodID()).isEqualTo(expectedId);

        BDDMockito.then(paymentMethodRepositoryMock).should().findById(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when id not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenIdNotFound() {
        long id = 1L;

        BDDMockito.given(paymentMethodRepositoryMock.findById(id))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> paymentMethodService.findByIdOrThrowBadRequestException(id))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(paymentMethodRepositoryMock).should().findById(ArgumentMatchers.anyLong());
    }

    //-------------------------------
    // save tests -------------------
    //-------------------------------
    @Test
    @DisplayName("save returns payment method when successful")
    void save_ReturnsPaymentMethod_WhenSuccessful() {
        PaymentMethodRequestBody paymentMethodRequestBody = PaymentMethodRequestBodyCreator.createPaymentMethodRequestBody();
        PaymentMethod paymentMethodToBeSaved = PaymentMethodCreator.createPaymentMethodToBeSaved();
        PaymentMethod paymentMethodSaved = PaymentMethodCreator.createValidPaymentMethod();

        BDDMockito.given(paymentMethodMapperMock.toPaymentMethod(paymentMethodRequestBody))
                .willReturn(paymentMethodToBeSaved);
        BDDMockito.given(paymentMethodRepositoryMock.save(paymentMethodToBeSaved))
                .willReturn(paymentMethodSaved);

        PaymentMethod result = paymentMethodService.save(paymentMethodRequestBody);

        Assertions.assertThat(result).isNotNull().isEqualTo(paymentMethodSaved);

        BDDMockito.then(paymentMethodMapperMock).should().toPaymentMethod(paymentMethodRequestBody);
        BDDMockito.then(paymentMethodRepositoryMock).should().save(paymentMethodToBeSaved);
    }

    //-------------------------------
    // update tests -----------------
    //-------------------------------
    @Test
    @DisplayName("update replaces payment method when successful")
    void update_UpdatesPaymentMethod_WhenSuccessful() {
        long paymentMethodId = 1L;
        PaymentMethodRequestBody paymentMethodRequestBody = PaymentMethodRequestBodyCreator.createUpdatedPaymentMethodRequestBody();
        PaymentMethod paymentMethod = PaymentMethodCreator.createValidPaymentMethod();
        PaymentMethod updateData = PaymentMethodCreator.createValidUpdatedPaymentMethod();

        BDDMockito.given(paymentMethodRepositoryMock.findById(paymentMethodId))
                .willReturn(Optional.of(paymentMethod));
        BDDMockito.given(paymentMethodMapperMock.toPaymentMethod(paymentMethodRequestBody))
                .willReturn(updateData);

        paymentMethodService.update(paymentMethodId, paymentMethodRequestBody);

        Assertions.assertThat(paymentMethod.getPaymentMethodName()).isEqualTo(updateData.getPaymentMethodName());

        BDDMockito.then(paymentMethodRepositoryMock).should().findById(paymentMethodId);
        BDDMockito.then(paymentMethodMapperMock).should().toPaymentMethod(paymentMethodRequestBody);
        BDDMockito.then(paymentMethodRepositoryMock).should().save(paymentMethod);
    }

    @Test
    @DisplayName("update throws BadRequestException when payment method not found")
    void update_ThrowsBadRequestException_WhenPaymentMethodNotFound() {
        long paymentMethodId = 1L;
        PaymentMethodRequestBody paymentMethodRequestBody = PaymentMethodRequestBodyCreator.createPaymentMethodRequestBody();

        BDDMockito.given(paymentMethodRepositoryMock.findById(paymentMethodId))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> paymentMethodService.update(paymentMethodId, paymentMethodRequestBody))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(paymentMethodRepositoryMock)
                .should(BDDMockito.never())
                .save(ArgumentMatchers.any(PaymentMethod.class));
        BDDMockito.then(paymentMethodMapperMock).shouldHaveNoInteractions();
    }

    //-------------------------------
    // delete tests -----------------
    //-------------------------------
    @Test
    @DisplayName("delete removes payment method when successful")
    void delete_RemovesPaymentMethod_WhenSuccessful() {
        long paymentMethodId = 1L;
        PaymentMethod paymentMethod = PaymentMethodCreator.createValidPaymentMethod();

        BDDMockito.given(paymentMethodRepositoryMock.findById(paymentMethodId))
                .willReturn(Optional.of(paymentMethod));

        paymentMethodService.delete(paymentMethodId);

        BDDMockito.then(paymentMethodRepositoryMock).should().findById(paymentMethodId);
        BDDMockito.then(paymentMethodRepositoryMock).should().delete(paymentMethod);
    }

    @Test
    @DisplayName("delete throws BadRequestException when payment method not found")
    void delete_ThrowsBadRequestException_WhenPaymentMethodNotFound() {
        long paymentMethodId = 1L;

        BDDMockito.given(paymentMethodRepositoryMock.findById(paymentMethodId))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> paymentMethodService.delete(paymentMethodId))
                .isInstanceOf(BadRequestException.class);

        BDDMockito.then(paymentMethodRepositoryMock)
                .should(BDDMockito.never())
                .delete(ArgumentMatchers.any(PaymentMethod.class));
    }
}
