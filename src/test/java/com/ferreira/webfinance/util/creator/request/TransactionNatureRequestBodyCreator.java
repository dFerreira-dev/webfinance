package com.ferreira.webfinance.util.creator.request;

import com.ferreira.webfinance.dto.request.TransactionNatureRequestBody;

public class TransactionNatureRequestBodyCreator {
    public static TransactionNatureRequestBody createTransactionNatureRequestBody() {
        return new TransactionNatureRequestBody("Fixed");
    }

    public static TransactionNatureRequestBody createUpdatedTransactionNatureRequestBody() {
        return new TransactionNatureRequestBody("Variable");
    }
}
