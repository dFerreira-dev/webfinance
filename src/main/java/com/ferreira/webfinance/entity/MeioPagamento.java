package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MeioPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(nullable = false)
    private String nomeMeioPagamento;
    //debito, credito, boleto, pix

}