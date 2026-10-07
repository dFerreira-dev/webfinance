package com.ferreira.webfinance.repository;

import com.ferreira.webfinance.entity.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
    
}
