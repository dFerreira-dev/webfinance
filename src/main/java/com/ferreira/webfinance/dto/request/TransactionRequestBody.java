package com.ferreira.webfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "The date of transaction cannot be empty or null")
    private LocalDate transactionDate;

    private LocalDate effectiveTransactionDate;

    @NotEmpty(message = "The description cannot bem empty or null")
    private String description;

    @NotEmpty(message = "The transaction type id cannot be empty or null ")
    private Long transactionTypeId;

    private Long transactionCategoryId;

    @NotEmpty(message = "The transaction nature id cannot be empty or null")
    private Long transactionNatureId;

    private Long paymentMethodId;

    @NotEmpty(message = "The account id cannot be empty or null")
    private Long accountId;

    @NotEmpty(message = "The amount cannot be empty or null")
    private BigDecimal amount;

}
