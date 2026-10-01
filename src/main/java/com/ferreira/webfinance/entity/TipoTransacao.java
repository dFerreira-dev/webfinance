package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TipoTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tipoTransacaoId;


    @Column(nullable = false)
    private String nomeTipoTransacao;
    //entrada, saida, transferencia

}
