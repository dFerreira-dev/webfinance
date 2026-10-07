package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TransactionNature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_nature_id")
    private Long transactionNatureId;

    @Column(name = "transaction_nature_name", nullable = false)
    private String transactionNatureName;
    //fixed, variable, investment

}