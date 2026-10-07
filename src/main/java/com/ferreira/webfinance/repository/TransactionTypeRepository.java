package com.ferreira.webfinance.repository;

import com.ferreira.webfinance.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    
}
