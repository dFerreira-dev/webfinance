package com.ferreira.webfinance.util.fixture;

import com.ferreira.webfinance.entity.*;
import com.ferreira.webfinance.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DatabaseFixtureHelper {
    // dependencies
    private final AccountRepository accountRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionNatureRepository transactionNatureRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final TransactionCategoryRepository transactionCategoryRepository;
    private final TransactionRepository transactionRepository;

    //----------------------------------------------------
    // methods to persist entities into in-memory database
    //----------------------------------------------------

    // persist Account
    public Account persistAccount(String name) {
        return accountRepository.save(Account.builder().accountName(name).build());
    }

    // persist Transaction Type
    public TransactionType persistTransactionType(String name) {
        return transactionTypeRepository.save(TransactionType.builder().transactionTypeName(name).build());
    }

    // persist Transaction Nature
    public TransactionNature persistTransactionNature(String name) {
        return transactionNatureRepository.save(TransactionNature.builder().transactionNatureName(name).build());
    }

    // persist Payment Method
    public PaymentMethod persistPaymentMethod(String name) {
        return paymentMethodRepository.save(PaymentMethod.builder().paymentMethodName(name).build());
    }

    // persist Transaction Category
    public TransactionCategory persistTransactionCategory(String name) {
        return transactionCategoryRepository.save(TransactionCategory.builder().transactionCategoryName(name).build());
    }

    // persist Transaction
    public Transaction persistTransaction(Account account, TransactionType type, TransactionNature nature, BigDecimal amount) {
        return transactionRepository.save(Transaction.builder()
                .account(account)
                .transactionType(type)
                .transactionNature(nature)
                .transactionDate(LocalDate.of(2026, 11, 1))
                .effectiveTransactionDate(LocalDate.of(2026, 11, 2))
                .description("Sample Transaction")
                .amount(amount)
                .build());
    }
}
