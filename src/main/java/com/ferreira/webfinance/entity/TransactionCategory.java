package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_category_id")
    private Long transactionCategoryId;

    @Column(name = "transaction_category_name", nullable = false)
    private String transactionCategoryName;
    //salary, vouchers, pension, etc.

}
