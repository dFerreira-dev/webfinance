package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriaTransacaoId;

    @Column(nullable = false)
    private String nomeCategoria;
    //salario, vale, pensao, etc

}
