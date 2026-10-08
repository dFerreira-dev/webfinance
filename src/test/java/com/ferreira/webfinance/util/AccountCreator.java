package com.ferreira.webfinance.util;

import com.ferreira.webfinance.entity.Account;

public class AccountCreator {
    public static Account createAccountToBeSaved() {
        return Account.builder()
                .accountName("BTG")
                .build();
    }

    public static Account createValidAccount(){
        return  Account.builder()
                .accountName("BTG")
                .accountId(1L)
                .build();
    }

    public static Account createValidUpdatedAccount() {
        return Account.builder()
                .accountName("Inter")
                .accountId(1L)
                .build();
    }

}
