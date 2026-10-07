package com.ferreira.webfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionNatureRequestBody {
    @NotEmpty(message = "The transaction nature name cannot be empty or null")
    private String trasactionNatureName;
}
