package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransactionNature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_nature_id")
    private Long transactionNatureId;

    @Column(name = "transaction_nature_name", nullable = false)
    private String transactionNatureName;
    //fixed, variable, investment

}