package com.ferreira.webfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;

public class ContaRequestBody {
    @NotEmpty(message = "The account name (nomeConta) cannot be empty or null")
    private String nomeConta;
}
