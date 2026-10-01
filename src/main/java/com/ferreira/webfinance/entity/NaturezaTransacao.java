package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class NaturezaTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long naturezaTransacaoId;

    @Column(nullable = false)
    private String nomeNaturezaTransacao;
    //fixo, variavel, investimento

}