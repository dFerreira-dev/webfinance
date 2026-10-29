package com.ferreira.webfinance.util;

import com.ferreira.webfinance.entity.PaymentMethod;

public class PaymentMethodCreator {
    public static PaymentMethod createPaymentMethodToBeSaved() {
        return PaymentMethod.builder()
                .paymentMethodName("Credit")
                .build();
    }

    public static PaymentMethod createValidPaymentMethod(){
        return PaymentMethod.builder()
                .paymentMethodName("Credit")
                .paymentMethodID(1L)
                .build();
    }

    public static PaymentMethod createValidUpdatedPaymentMethod(){
        return PaymentMethod.builder()
                .paymentMethodName("Debit")
                .paymentMethodID(1L)
                .build();
    }
}
