package com.ferreira.webfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCategoryRequestBody {
    @NotEmpty(message = "The category name cannot be empty or null")
    private String transactionCategoryName;
}
