package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.PaymentMethodRequestBody;
import com.ferreira.webfinance.entity.PaymentMethod;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.PaymentMethodMapper;
import com.ferreira.webfinance.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    public List<PaymentMethod> findAll(){
        /*
         * Returns a list of all payments methods
         */
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod findByIdOrThrowBadRequestException(long id) {
        /*
         * Returns a payments method if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return paymentMethodRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Payment Method Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public PaymentMethod save(PaymentMethodRequestBody paymentMethodRequestBody) {
        /*
         * save payment method
         * - This method expect an paymentMethodRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new payment method
         * */
        return paymentMethodRepository.save(paymentMethodMapper.toPaymentMethod(paymentMethodRequestBody));
    }

    public void update(long id, PaymentMethodRequestBody paymentMethodRequestBody) {
        /*
         * update payment method
         * - This method firstly try to find an original payment method by id and returns it;
         * - If it's found, then a temporary payment method is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original payment method
         * - Finally, the repository saves the payment method updated
         * */
        PaymentMethod paymentMethod = findByIdOrThrowBadRequestException(id);

        PaymentMethod updatedData = paymentMethodMapper.toPaymentMethod(paymentMethodRequestBody);
        paymentMethod.setPaymentMethodName(updatedData.getPaymentMethodName());

        paymentMethodRepository.save(paymentMethod);
    }

    public void delete(long id) {

        /*
         * Delete existing payment method using id
         * If id not exists, it throws a BadRequestExcepetion
         * */
        paymentMethodRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
