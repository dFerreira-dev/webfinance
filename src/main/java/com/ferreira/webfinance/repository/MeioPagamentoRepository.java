package com.ferreira.webfinance.repository;

import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.MeioPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeioPagamentoRepository extends JpaRepository<MeioPagamento, Long> {
    
}
