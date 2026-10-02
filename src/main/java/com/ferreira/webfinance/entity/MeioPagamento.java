package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MeioPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meio_pagamento_id")
    Long meioPagamentoId;

    @Column(name = "nome_meio_pagamento", nullable = false)
    private String nomeMeioPagamento;
    //debito, credito, boleto, pix

}