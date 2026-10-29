package com.ferreira.webfinance.util;

import com.ferreira.webfinance.dto.request.TransactionRequestBody;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequestBodyCreator {
    public static TransactionRequestBody createTransactionRequestBody() {
        return TransactionRequestBody.builder()
                .transactionDate(LocalDate.of(2026, 10, 1))
                .effectiveTransactionDate(LocalDate.of(2026, 10, 2))
                .description("Grocery shopping")
                .transactionTypeId(1L)
                .transactionCategoryId(1L)
                .transactionNatureId(1L)
                .paymentMethodId(1L)
                .accountId(1L)
                .amount(new BigDecimal("150.00"))
                .build();
    }
}
