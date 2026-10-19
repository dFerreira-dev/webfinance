package com.ferreira.webfinance.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestBody {

    @NotNull(message = "The date of transaction cannot be empty or null")
    private LocalDate transactionDate;

    private LocalDate effectiveTransactionDate;

    @NotBlank(message = "The description cannot bem empty or null")
    private String description;

    @NotNull(message = "The transaction type id cannot be empty or null ")
    private Long transactionTypeId;

    private Long transactionCategoryId;

    @NotNull(message = "The transaction nature id cannot be empty or null")
    private Long transactionNatureId;

    private Long paymentMethodId;

    @NotNull(message = "The account id cannot be empty or null")
    private Long accountId;

    @NotNull(message = "The amount cannot be empty or null")
    private BigDecimal amount;

}
