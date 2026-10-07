package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_type_id")
    private Long transactionTypeId;


    @Column(name = "transaction_type_name", nullable = false)
    private String transactionTypeName;
    //inflow, outflow, tranfer

}
