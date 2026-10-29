package com.ferreira.webfinance.util.creator.request;

import com.ferreira.webfinance.dto.request.PaymentMethodRequestBody;

public class PaymentMethodRequestBodyCreator {
    public static PaymentMethodRequestBody createPaymentMethodRequestBody() {
        return new PaymentMethodRequestBody("Credit");
    }

    public static PaymentMethodRequestBody createUpdatedPaymentMethodRequestBody() {
        return new PaymentMethodRequestBody("Debit");
    }
}
