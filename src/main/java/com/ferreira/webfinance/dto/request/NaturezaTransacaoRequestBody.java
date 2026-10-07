package com.ferreira.webfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaturezaTransacaoRequestBody {
    @NotEmpty(message = "The nature transation name (nomeNaturezaTransacao) cannot be empty or null")
    private String nomeNaturezaTransacao;
}
