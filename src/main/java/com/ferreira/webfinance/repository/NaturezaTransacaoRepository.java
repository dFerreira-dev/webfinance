package com.ferreira.webfinance.repository;

import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.NaturezaTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NaturezaTransacaoRepository extends JpaRepository<NaturezaTransacao, Long> {
    
}
