package com.ferreira.webfinance.repository;

import com.ferreira.webfinance.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
