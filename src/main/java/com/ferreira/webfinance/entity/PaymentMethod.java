package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    Long paymentMethodID;

    @Column(name = "payment_method_name", nullable = false)
    private String paymentMethodName;
    //debit, credit, bank slip, pix

}