package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.PaymentMethodRequestBody;
import com.ferreira.webfinance.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PaymentMethodMapper {

    public static final PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);

    public abstract PaymentMethod toPaymentMethod(PaymentMethodRequestBody paymentMethodRequestBody);
}
