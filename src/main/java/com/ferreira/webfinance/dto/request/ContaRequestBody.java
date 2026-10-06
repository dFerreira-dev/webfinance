package com.ferreira.webfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaRequestBody {
    @NotEmpty(message = "The account name (nomeConta) cannot be empty or null")
    private String nomeConta;
}
