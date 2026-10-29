package com.ferreira.webfinance.util.creator.request;

import com.ferreira.webfinance.dto.request.TransactionCategoryRequestBody;

public class TransactionCategoryRequestBodyCreator {
    public static TransactionCategoryRequestBody createTransactionCategoryRequestBody() {
        return new TransactionCategoryRequestBody("Salary");
    }

    public static TransactionCategoryRequestBody createUpdatedTransactionCategoryRequestBody() {
        return new TransactionCategoryRequestBody("Voucher");
    }
}
