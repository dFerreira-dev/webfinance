package com.ferreira.webfinance.util.creator.request;

import com.ferreira.webfinance.dto.request.TransactionTypeRequestBody;

public class TransactionTypeRequestBodyCreator {
    public static TransactionTypeRequestBody createTransactionTypeRequestBody() {
        return new TransactionTypeRequestBody("Inflow");
    }

    public static TransactionTypeRequestBody createUpdatedTransactionTypeRequestBody() {
        return new TransactionTypeRequestBody("Outflow");
    }
}
