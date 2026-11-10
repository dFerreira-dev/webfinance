package com.ferreira.webfinance.integration.controller;

import com.ferreira.webfinance.dto.request.TransactionRequestBody;
import com.ferreira.webfinance.entity.*;
import com.ferreira.webfinance.integration.config.BaseIntegrationTest;
import com.ferreira.webfinance.repository.TransactionRepository;
import com.ferreira.webfinance.util.fixture.DatabaseFixtureHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerIT extends BaseIntegrationTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DatabaseFixtureHelper fixtureHelper;

    @Test
    @DisplayName("GET /transactions should return list of transactions")
    void listAll_ShouldReturnListOfTransactions_WhenTransactionsExist() throws Exception {
        Account account = fixtureHelper.persistAccount("Checking");
        TransactionType type = fixtureHelper.persistTransactionType("Expense");
        TransactionNature nature = fixtureHelper.persistTransactionNature("Fixed");

        Transaction transaction = fixtureHelper.persistTransaction(account, type, nature, new BigDecimal("100.00"));

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.transactionId == " + transaction.getTransactionId() + ")].description").value("Sample Transaction"));
    }

    @Test
    @DisplayName("GET /transactions/{id} should return transaction when id exists")
    void findById_ShouldReturnTransaction_WhenIdExists() throws Exception {
        Account account = fixtureHelper.persistAccount("Savings");
        TransactionType type = fixtureHelper.persistTransactionType("Income");
        TransactionNature nature = fixtureHelper.persistTransactionNature("Variable");

        Transaction transaction = fixtureHelper.persistTransaction(account, type, nature, new BigDecimal("250.00"));

        mockMvc.perform(get("/transactions/{id}", transaction.getTransactionId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(transaction.getTransactionId()))
                .andExpect(jsonPath("$.description").value("Sample Transaction"))
                .andExpect(jsonPath("$.amount").value(250.00));
    }

    @Test
    @DisplayName("GET /transactions/{id} should return 400 when id does not exist")
    void findById_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/transactions/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /transactions should create transaction when payload and references are valid")
    void save_ShouldPersistTransaction_WhenPayloadIsValid() throws Exception {
        Account account = fixtureHelper.persistAccount("Investment Account");
        TransactionType type = fixtureHelper.persistTransactionType("Investment");
        TransactionNature nature = fixtureHelper.persistTransactionNature("Long-term");
        PaymentMethod paymentMethod = fixtureHelper.persistPaymentMethod("Wire Transfer");
        TransactionCategory category = fixtureHelper.persistTransactionCategory("Stocks");

        TransactionRequestBody request = TransactionRequestBody.builder()
                .transactionDate(LocalDate.of(2026, 11, 3))
                .effectiveTransactionDate(LocalDate.of(2026, 11, 4))
                .description("Bought AAPL")
                .transactionTypeId(type.getTransactionTypeId())
                .transactionNatureId(nature.getTransactionNatureId())
                .paymentMethodId(paymentMethod.getPaymentMethodID())
                .transactionCategoryId(category.getTransactionCategoryId())
                .accountId(account.getAccountId())
                .amount(new BigDecimal("500.00"))
                .build();

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").isNumber())
                .andExpect(jsonPath("$.description").value("Bought AAPL"))
                .andExpect(jsonPath("$.amount").value(500.00))
                .andExpect(jsonPath("$.account.accountId").value(account.getAccountId()))
                .andExpect(jsonPath("$.transactionType.transactionTypeId").value(type.getTransactionTypeId()))
                .andExpect(jsonPath("$.transactionNature.transactionNatureId").value(nature.getTransactionNatureId()))
                .andExpect(jsonPath("$.paymentMethod.paymentMethodID").value(paymentMethod.getPaymentMethodID()))
                .andExpect(jsonPath("$.transactionCategory.transactionCategoryId").value(category.getTransactionCategoryId()));

        assertThat(transactionRepository.findAll())
                .filteredOn(t -> "Bought AAPL".equals(t.getDescription()))
                .hasSize(1);
    }

    @Test
    @DisplayName("POST /transactions should return 400 when account does not exist")
    void save_ShouldReturn400_WhenAccountDoesNotExist() throws Exception {
        TransactionType type = fixtureHelper.persistTransactionType("Expense");
        TransactionNature nature = fixtureHelper.persistTransactionNature("Fixed");

        TransactionRequestBody request = TransactionRequestBody.builder()
                .transactionDate(LocalDate.now())
                .description("Invalid Account Test")
                .transactionTypeId(type.getTransactionTypeId())
                .transactionNatureId(nature.getTransactionNatureId())
                .accountId(99999L)
                .amount(new BigDecimal("50.00"))
                .build();

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /transactions should return 400 when transaction type does not exist")
    void save_ShouldReturn400_WhenTransactionTypeDoesNotExist() throws Exception {
        Account account = fixtureHelper.persistAccount("Account");
        TransactionNature nature = fixtureHelper.persistTransactionNature("Fixed");

        TransactionRequestBody request = TransactionRequestBody.builder()
                .transactionDate(LocalDate.now())
                .description("Invalid Type Test")
                .transactionTypeId(99999L)
                .transactionNatureId(nature.getTransactionNatureId())
                .accountId(account.getAccountId())
                .amount(new BigDecimal("50.00"))
                .build();

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /transactions should return 400 when transaction nature does not exist")
    void save_ShouldReturn400_WhenTransactionNatureDoesNotExist() throws Exception {
        Account account = fixtureHelper.persistAccount("Account");
        TransactionType type = fixtureHelper.persistTransactionType("Expense");

        TransactionRequestBody request = TransactionRequestBody.builder()
                .transactionDate(LocalDate.now())
                .description("Invalid Nature Test")
                .transactionTypeId(type.getTransactionTypeId())
                .transactionNatureId(99999L)
                .accountId(account.getAccountId())
                .amount(new BigDecimal("50.00"))
                .build();

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /transactions should return 400 when description is blank")
    void save_ShouldReturn400_WhenDescriptionIsBlank() throws Exception {
        Account account = fixtureHelper.persistAccount("Account");
        TransactionType type = fixtureHelper.persistTransactionType("Expense");
        TransactionNature nature = fixtureHelper.persistTransactionNature("Fixed");

        TransactionRequestBody request = TransactionRequestBody.builder()
                .transactionDate(LocalDate.now())
                .description("")
                .transactionTypeId(type.getTransactionTypeId())
                .transactionNatureId(nature.getTransactionNatureId())
                .accountId(account.getAccountId())
                .amount(new BigDecimal("50.00"))
                .build();

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /transactions/{id} should update existing transaction")
    void update_ShouldUpdateTransaction_WhenPayloadIsValid() throws Exception {
        Account account = fixtureHelper.persistAccount("Old Account");
        Account newAccount = fixtureHelper.persistAccount("New Account");
        TransactionType type = fixtureHelper.persistTransactionType("Old Type");
        TransactionNature nature = fixtureHelper.persistTransactionNature("Old Nature");

        Transaction existing = fixtureHelper.persistTransaction(account, type, nature, new BigDecimal("100.00"));

        TransactionRequestBody updateRequest = TransactionRequestBody.builder()
                .transactionDate(LocalDate.of(2026, 11, 10))
                .effectiveTransactionDate(LocalDate.of(2026, 11, 11))
                .description("Updated Description")
                .transactionTypeId(type.getTransactionTypeId())
                .transactionNatureId(nature.getTransactionNatureId())
                .accountId(newAccount.getAccountId())
                .amount(new BigDecimal("350.00"))
                .build();

        mockMvc.perform(put("/transactions/{id}", existing.getTransactionId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNoContent());

        Transaction updated = transactionRepository.findById(existing.getTransactionId()).orElseThrow();
        assertThat(updated.getDescription()).isEqualTo("Updated Description");
        assertThat(updated.getAmount()).isEqualByComparingTo("350.00");
        assertThat(updated.getAccount().getAccountId()).isEqualTo(newAccount.getAccountId());
    }

    @Test
    @DisplayName("PUT /transactions/{id} should return 400 when id does not exist")
    void update_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        Account account = fixtureHelper.persistAccount("Account");
        TransactionType type = fixtureHelper.persistTransactionType("Expense");
        TransactionNature nature = fixtureHelper.persistTransactionNature("Fixed");

        TransactionRequestBody updateRequest = TransactionRequestBody.builder()
                .transactionDate(LocalDate.now())
                .description("Non-existent Update")
                .transactionTypeId(type.getTransactionTypeId())
                .transactionNatureId(nature.getTransactionNatureId())
                .accountId(account.getAccountId())
                .amount(new BigDecimal("100.00"))
                .build();

        mockMvc.perform(put("/transactions/{id}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /transactions/{id} should remove transaction")
    void delete_ShouldRemoveTransaction_WhenIdExists() throws Exception {
        Account account = fixtureHelper.persistAccount("Account");
        TransactionType type = fixtureHelper.persistTransactionType("Expense");
        TransactionNature nature = fixtureHelper.persistTransactionNature("Fixed");

        Transaction transaction = fixtureHelper.persistTransaction(account, type, nature, new BigDecimal("80.00"));

        mockMvc.perform(delete("/transactions/{id}", transaction.getTransactionId()))
                .andExpect(status().isNoContent());

        assertThat(transactionRepository.findById(transaction.getTransactionId())).isEmpty();
    }

    @Test
    @DisplayName("DELETE /transactions/{id} should return 400 when id does not exist")
    void delete_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(delete("/transactions/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }
}
