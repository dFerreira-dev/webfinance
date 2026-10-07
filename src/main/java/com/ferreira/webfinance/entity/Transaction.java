package com.ferreira.webfinance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    //data em que foi gerada a transação
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    //data efetiva em que o amount sai ou entra
    @Column(name = "effective_transaction_date")
    private LocalDate effectiveTransactionDate;

    //descrição
    @Column(name = "description", nullable = false)
    private String description;

    //tipo
    @ManyToOne(optional = false)
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

    //categoria
    @ManyToOne
    @JoinColumn(name = "transaction_category_id")
    private TransactionCategory transactionCategory;

    //natureza
    @ManyToOne(optional = false)
    @JoinColumn(name = "transaction_nature_id", nullable = false)
    private TransactionNature transactionNature;

    //meio pagamento
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    //account
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    //amount
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

}
