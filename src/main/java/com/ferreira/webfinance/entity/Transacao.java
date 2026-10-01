package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransacao;

    //data em que foi gerada a transação
    @Column(nullable = false)
    private LocalDate dataTransacao;

    //data efetiva em que o valor sai ou entra
    private LocalDate dataEfetivaPagamento;

    //descrição
    @Column(nullable = false)
    private String descricao;

    //tipo
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipoTransacaoId", nullable = false)
    private TipoTransacao tipoTransacao;

    //categoria
    @ManyToOne
    @JoinColumn(name = "categoriaTransacaoId")
    private CategoriaTransacao categoriaTransacao;

    //natureza
    @JoinColumn(name = "naturezaTransacaoId", nullable = false)
    private NaturezaTransacao naturezaTransacao;

    //meio pagamento
    @ManyToOne
    @JoinColumn(name = "meioPagamentoId")
    private MeioPagamento meioPagamento;

    //conta
    @ManyToOne(optional = false)
    @JoinColumn(name = "contaId", nullable = false)
    private Conta conta;

    //valor
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

}
