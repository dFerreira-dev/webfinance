package com.ferreira.webfinance.util;

import com.ferreira.webfinance.entity.TransactionNature;

public class TransactionNatureCreator {
    public static TransactionNature createTransactionNatureToBeSaved() {
        return TransactionNature.builder()
                .transactionNatureName("Fixed")
                .build();
    }

    public static TransactionNature createValidTransactionNature() {
        return TransactionNature.builder()
                .transactionNatureName("Fixed")
                .transactionNatureId(1L)
                .build();
    }

    public static TransactionNature createValidUpdatedTransactionNature() {
        return TransactionNature.builder()
                .transactionNatureName("Variable")
                .transactionNatureId(1L)
                .build();
    }
}
