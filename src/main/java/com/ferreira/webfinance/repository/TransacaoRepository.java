package com.ferreira.webfinance.repository;

import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
