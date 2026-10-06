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

    @NotEmpty
    private LocalDate dataTransacao;

    private LocalDate dataEfetivaPagamento;

    @NotEmpty
    private String descricao;

    @NotEmpty
    private Long tipoTransacaoId;

    private Long categoriaTransacaoId;

    @NotEmpty
    private Long naturezaTransacaoId;

    private Long meioPagamentoId;

    @NotEmpty
    private Long contaID;

    @NotEmpty
    private BigDecimal valor;

}
