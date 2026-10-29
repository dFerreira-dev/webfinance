package com.ferreira.webfinance.util.creator.entity;

import com.ferreira.webfinance.entity.TransactionCategory;

public class TransactionCategoryCreator {
    public static TransactionCategory createTransactionCategoryToBeSaved() {
        return TransactionCategory.builder()
                .transactionCategoryName("Salary")
                .build();
    }

    public static TransactionCategory createValidTransactionCategory(){
        return TransactionCategory.builder()
                .transactionCategoryName("Salary")
                .transactionCategoryId(1L)
                .build();
    }

    public static TransactionCategory createValidUpdatedTransactionCategory(){
        return TransactionCategory.builder()
                .transactionCategoryName("Voucher")
                .transactionCategoryId(1L)
                .build();
    }
}
