package com.ferreira.webfinance.repository;

import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long> {
    
}
