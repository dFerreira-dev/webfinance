package com.ferreira.webfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TransacaoResquestBody {

    @NotEmpty(message = "The date of transaction (dataTransacao) cannot be empty or null")
    private LocalDate dataTransacao;

    private LocalDate dataEfetivaPagamento;

    @NotEmpty(message = "The description (descricao) cannot bem empty or null")
    private String descricao;

    @NotEmpty(message = "The transaction type id (tipoTransacaoId) cannot be empty or null ")
    private Long tipoTransacaoId;

    private Long categoriaTransacaoId;

    @NotEmpty(message = "The nature of the transaction id cannot be empty or null.")
    private Long naturezaTransacaoId;

    private Long meioPagamentoId;

    @NotEmpty(message = "The account id (contaId) cannot be empty or null")
    private Long contaId;

    @NotEmpty(message = "The amount/value (valor) cannot be empty or null")
    private BigDecimal valor;

}
