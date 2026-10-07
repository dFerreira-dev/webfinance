package com.ferreira.webfinance.repository;

import com.ferreira.webfinance.entity.TransactionNature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionNatureRepository extends JpaRepository<TransactionNature, Long> {
    
}
