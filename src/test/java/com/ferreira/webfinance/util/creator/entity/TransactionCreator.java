package com.ferreira.webfinance.util.creator.entity;

import com.ferreira.webfinance.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionCreator {
    public static Transaction createTransactionToBeSaved() {
        return Transaction.builder()
                .transactionDate(LocalDate.of(2026, 10, 1))
                .effectiveTransactionDate(LocalDate.of(2026, 10, 2))
                .description("Grocery shopping")
                .transactionType(TransactionTypeCreator.createValidTransactionType())
                //.transactionCategory(TransactionCategoryCreator.createValidTransactionCategory()) // OPTIONAL
                .transactionNature(TransactionNatureCreator.createValidTransactionNature())
                //.paymentMethod(PaymentMethodCreator.createValidPaymentMethod()) // OPTIONAL
                .account(AccountCreator.createValidAccount())
                .amount(new BigDecimal("150.00"))
                .build();
    }

    public static Transaction createValidTransaction() {
        return Transaction.builder()
                .transactionId(1L)
                .transactionDate(LocalDate.of(2026, 10, 1))
                .effectiveTransactionDate(LocalDate.of(2026, 10, 2))
                .description("Grocery shopping")
                .transactionType(TransactionTypeCreator.createValidTransactionType())
                //.transactionCategory(TransactionCategoryCreator.createValidTransactionCategory()) // OPTIONAL
                .transactionNature(TransactionNatureCreator.createValidTransactionNature())
                //.paymentMethod(PaymentMethodCreator.createValidPaymentMethod()) // OPTIONAL
                .account(AccountCreator.createValidAccount())
                .amount(new BigDecimal("150.00"))
                .build();
    }

    public static Transaction createValidUpdatedTransaction() {
        return Transaction.builder()
                .transactionId(1L)
                .transactionDate(LocalDate.of(2026, 11, 19))
                .effectiveTransactionDate(LocalDate.of(2026, 11, 19))
                .description("GTA VI")
                .transactionType(TransactionTypeCreator.createValidTransactionType())
                //.transactionCategory(TransactionCategoryCreator.createValidTransactionCategory()) // OPTIONAL
                .transactionNature(TransactionNatureCreator.createValidTransactionNature())
                //.paymentMethod(PaymentMethodCreator.createValidPaymentMethod()) // OPTIONAL
                .amount(new BigDecimal("200.00"))
                .build();
    }
}
