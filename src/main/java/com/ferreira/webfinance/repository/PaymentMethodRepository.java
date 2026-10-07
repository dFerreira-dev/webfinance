package com.ferreira.webfinance.repository;

import com.ferreira.webfinance.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    
}
