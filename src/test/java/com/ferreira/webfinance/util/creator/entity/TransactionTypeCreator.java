package com.ferreira.webfinance.util;

import com.ferreira.webfinance.entity.TransactionType;

public class TransactionTypeCreator {
    public static TransactionType createTransactionTypeToBeSaved() {
        return TransactionType.builder()
                .transactionTypeName("Inflow")
                .build();
    }

    public static TransactionType createValidTransactionType() {
        return TransactionType.builder()
                .transactionTypeName("Inflow")
                .transactionTypeId(1L)
                .build();
    }

    public static TransactionType createValidUpdatedTransactionType(){
        return TransactionType.builder()
                .transactionTypeName("Outflow")
                .transactionTypeId(1L)
                .build();
    }
}
