package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.PaymentMethodRequestBody;
import com.ferreira.webfinance.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethod toPaymentMethod(PaymentMethodRequestBody paymentMethodRequestBody);
}
