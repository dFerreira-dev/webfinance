package com.ferreira.webfinance.util.creator.request;

import com.ferreira.webfinance.dto.request.AccountRequestBody;

public class AccountRequestBodyCreator {
    public static AccountRequestBody createAccountRequestBody() {
        return new AccountRequestBody("BTG");
    }

    public static AccountRequestBody createUpdatedAccountRequestBody() {
        return new AccountRequestBody("Inter");
    }
}
