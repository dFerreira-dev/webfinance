package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transacao_id")
    private Long transacaoId;

    //data em que foi gerada a transação
    @Column(name = "data_transacao", nullable = false)
    private LocalDate dataTransacao;

    //data efetiva em que o valor sai ou entra
    @Column(name = "data_efetiva_pagamento")
    private LocalDate dataEfetivaPagamento;

    //descrição
    @Column(name = "descicao", nullable = false)
    private String descricao;

    //tipo
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_transacao_id", nullable = false)
    private TipoTransacao tipoTransacao;

    //categoria
    @ManyToOne
    @JoinColumn(name = "categoria_transacao_id")
    private CategoriaTransacao categoriaTransacao;

    //natureza
    @ManyToOne(optional = false)
    @JoinColumn(name = "natureza_transacao_id", nullable = false)
    private NaturezaTransacao naturezaTransacao;

    //meio pagamento
    @ManyToOne
    @JoinColumn(name = "meio_pagamento_id")
    private MeioPagamento meioPagamento;

    //conta
    @ManyToOne(optional = false)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    //valor
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

}
