package com.ferreira.webfinance.integration.controller;

import com.ferreira.webfinance.dto.request.TransactionNatureRequestBody;
import com.ferreira.webfinance.entity.TransactionNature;
import com.ferreira.webfinance.integration.config.BaseIntegrationTest;
import com.ferreira.webfinance.repository.TransactionNatureRepository;
import com.ferreira.webfinance.util.fixture.DatabaseFixtureHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionNatureControllerIT extends BaseIntegrationTest {

    @Autowired
    private TransactionNatureRepository transactionNatureRepository;

    @Autowired
    private DatabaseFixtureHelper fixtureHelper;

    @Test
    @DisplayName("GET /transaction-natures should return list of natures")
    void findAll_ShouldReturnListOfNatures_WhenNaturesExist() throws Exception {
        TransactionNature saved = fixtureHelper.persistTransactionNature("Fixed Expense");

        mockMvc.perform(get("/transaction-natures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.transactionNatureId == " + saved.getTransactionNatureId() + ")].transactionNatureName").value("Fixed Expense"));
    }

    @Test
    @DisplayName("GET /transaction-natures/{id} should return nature when id exists")
    void findById_ShouldReturnNature_WhenIdExists() throws Exception {
        TransactionNature saved = fixtureHelper.persistTransactionNature("Variable Expense");

        mockMvc.perform(get("/transaction-natures/{id}", saved.getTransactionNatureId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionNatureId").value(saved.getTransactionNatureId()))
                .andExpect(jsonPath("$.transactionNatureName").value("Variable Expense"));
    }

    @Test
    @DisplayName("GET /transaction-natures/{id} should return 400 when id does not exist")
    void findById_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/transaction-natures/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /transaction-natures should create nature and persist in database")
    void save_ShouldPersistNature_WhenPayloadIsValid() throws Exception {
        TransactionNatureRequestBody request = new TransactionNatureRequestBody("Investment");

        mockMvc.perform(post("/transaction-natures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionNatureId").isNumber())
                .andExpect(jsonPath("$.transactionNatureName").value("Investment"));

        assertThat(transactionNatureRepository.findAll())
                .filteredOn(n -> n.getTransactionNatureName().equals("Investment"))
                .hasSize(1);
    }

    @Test
    @DisplayName("POST /transaction-natures should return 400 when name is empty")
    void save_ShouldReturn400_WhenNameIsEmpty() throws Exception {
        TransactionNatureRequestBody invalidRequest = new TransactionNatureRequestBody("");

        mockMvc.perform(post("/transaction-natures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /transaction-natures/{id} should update existing nature")
    void update_ShouldUpdateNature_WhenPayloadIsValid() throws Exception {
        TransactionNature saved = fixtureHelper.persistTransactionNature("Old Nature");
        TransactionNatureRequestBody updateRequest = new TransactionNatureRequestBody("New Nature");

        mockMvc.perform(put("/transaction-natures/{id}", saved.getTransactionNatureId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNoContent());

        TransactionNature updated = transactionNatureRepository.findById(saved.getTransactionNatureId()).orElseThrow();
        assertThat(updated.getTransactionNatureName()).isEqualTo("New Nature");
    }

    @Test
    @DisplayName("PUT /transaction-natures/{id} should return 400 when id does not exist")
    void update_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        TransactionNatureRequestBody updateRequest = new TransactionNatureRequestBody("New Nature");

        mockMvc.perform(put("/transaction-natures/{id}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /transaction-natures/{id} should remove nature")
    void delete_ShouldRemoveNature_WhenIdExists() throws Exception {
        TransactionNature saved = fixtureHelper.persistTransactionNature("To Delete");

        mockMvc.perform(delete("/transaction-natures/{id}", saved.getTransactionNatureId()))
                .andExpect(status().isNoContent());

        assertThat(transactionNatureRepository.findById(saved.getTransactionNatureId())).isEmpty();
    }

    @Test
    @DisplayName("DELETE /transaction-natures/{id} should return 400 when id does not exist")
    void delete_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(delete("/transaction-natures/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }
}
