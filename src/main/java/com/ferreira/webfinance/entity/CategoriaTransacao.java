package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CategoriaTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_transacao_id")
    private Long categoriaTransacaoId;

    @Column(name = "nome_categoria", nullable = false)
    private String nomeCategoria;
    //salario, vale, pensao, etc

}
