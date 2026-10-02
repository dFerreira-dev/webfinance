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
public class TipoTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_transacao_id")
    private Long tipoTransacaoId;


    @Column(name = "nome_tipo_transacao", nullable = false)
    private String nomeTipoTransacao;
    //entrada, saida, transferencia

}
