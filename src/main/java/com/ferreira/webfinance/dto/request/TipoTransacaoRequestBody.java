package com.ferreira.webfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoTransacaoRequestBody {
    @NotEmpty(message = "The transaction type name (nomeTipoTransacao) cannot be empty or null")
    private String nomeTipoTransacao;
}
