package com.ferreira.webfinance.integration.controller;

import com.ferreira.webfinance.dto.request.AccountRequestBody;
import com.ferreira.webfinance.entity.Account;
import com.ferreira.webfinance.integration.config.BaseIntegrationTest;
import com.ferreira.webfinance.repository.AccountRepository;
import com.ferreira.webfinance.util.fixture.DatabaseFixtureHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerIT extends BaseIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DatabaseFixtureHelper fixtureHelper;

    @Test
    @DisplayName("GET /accounts should return list of accounts")
    void findAll_ShouldReturnListOfAccounts_WhenAccountsExist() throws Exception {
        Account savedAccount = fixtureHelper.persistAccount("Nubank");

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.accountId == " + savedAccount.getAccountId() + ")].accountName").value("Nubank"));
    }

    @Test
    @DisplayName("GET /accounts/{id} should return account when id exists")
    void findById_ShouldReturnAccount_WhenIdExists() throws Exception {
        Account savedAccount = fixtureHelper.persistAccount("Santander");

        mockMvc.perform(get("/accounts/{id}", savedAccount.getAccountId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(savedAccount.getAccountId()))
                .andExpect(jsonPath("$.accountName").value("Santander"));
    }

    @Test
    @DisplayName("GET /accounts/{id} should return 400 when id does not exist")
    void findById_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/accounts/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /accounts should create account and persist in database")
    void save_ShouldPersistAccount_WhenPayloadIsValid() throws Exception {
        AccountRequestBody request = new AccountRequestBody("Checking Account");

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").isNumber())
                .andExpect(jsonPath("$.accountName").value("Checking Account"));

        assertThat(accountRepository.findAll())
                .filteredOn(a -> a.getAccountName().equals("Checking Account"))
                .hasSize(1);
    }

    @Test
    @DisplayName("POST /accounts should return 400 when accountName is blank")
    void save_ShouldReturn400_WhenAccountNameIsBlank() throws Exception {
        AccountRequestBody invalidRequest = new AccountRequestBody("");

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /accounts/{id} should update existing account")
    void update_ShouldUpdateAccount_WhenPayloadIsValid() throws Exception {
        Account savedAccount = fixtureHelper.persistAccount("Old Name");
        AccountRequestBody updateRequest = new AccountRequestBody("Updated Name");

        mockMvc.perform(put("/accounts/{id}", savedAccount.getAccountId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNoContent());

        Account updated = accountRepository.findById(savedAccount.getAccountId()).orElseThrow();
        assertThat(updated.getAccountName()).isEqualTo("Updated Name");
    }

    @Test
    @DisplayName("PUT /accounts/{id} should return 400 when id does not exist")
    void update_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        AccountRequestBody updateRequest = new AccountRequestBody("Updated Name");

        mockMvc.perform(put("/accounts/{id}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /accounts/{id} should remove account")
    void delete_ShouldRemoveAccount_WhenIdExists() throws Exception {
        Account savedAccount = fixtureHelper.persistAccount("To Delete");

        mockMvc.perform(delete("/accounts/{id}", savedAccount.getAccountId()))
                .andExpect(status().isNoContent());

        assertThat(accountRepository.findById(savedAccount.getAccountId())).isEmpty();
    }

    @Test
    @DisplayName("DELETE /accounts/{id} should return 400 when id does not exist")
    void delete_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(delete("/accounts/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }
}
