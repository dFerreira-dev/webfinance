package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class NaturezaTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "natureza_transacao_id")
    private Long naturezaTransacaoId;

    @Column(name = "nome_natureza_transacao", nullable = false)
    private String nomeNaturezaTransacao;
    //fixo, variavel, investimento

}