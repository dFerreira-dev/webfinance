package com.ferreira.webfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;

public class MeioPagamentoRequestBody {
    @NotEmpty(message = "The payment method name (nomeMeioPamaneto) cannot be empty or null")
    private String nomeMeioPagamento;
}
