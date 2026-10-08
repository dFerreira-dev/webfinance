package com.ferreira.webfinance.util;

import com.ferreira.webfinance.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionCreator {
    public static Transaction createTransactionToBeSaved() {
        return Transaction.builder()
                .transactionDate(LocalDate.of(2026, 10, 1))
                .effectiveTransactionDate(LocalDate.of(2026, 10, 2))
                .description("Grocery shopping")
                .amount(new BigDecimal("150.00"))
                .build();
    }

    public static Transaction createValidTransaction() {
        return Transaction.builder()
                .transactionId(1L)
                .transactionDate(LocalDate.of(2026, 10, 1))
                .effectiveTransactionDate(LocalDate.of(2026, 10, 2))
                .description("Grocery shopping")
                .amount(new BigDecimal("150.00"))
                .build();
    }

    public static Transaction createValidUpdatedTransaction() {
        return Transaction.builder()
                .transactionId(1L)
                .transactionDate(LocalDate.of(2026, 10, 5))
                .effectiveTransactionDate(LocalDate.of(2026, 10, 6))
                .description("Updated grocery shopping")
                .amount(new BigDecimal("200.00"))
                .build();
    }
}
