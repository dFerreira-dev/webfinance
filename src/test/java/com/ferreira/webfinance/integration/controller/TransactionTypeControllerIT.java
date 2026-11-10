package com.ferreira.webfinance.integration.controller;

import com.ferreira.webfinance.dto.request.TransactionTypeRequestBody;
import com.ferreira.webfinance.entity.TransactionType;
import com.ferreira.webfinance.integration.config.BaseIntegrationTest;
import com.ferreira.webfinance.repository.TransactionTypeRepository;
import com.ferreira.webfinance.util.fixture.DatabaseFixtureHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionTypeControllerIT extends BaseIntegrationTest {

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private DatabaseFixtureHelper fixtureHelper;

    @Test
    @DisplayName("GET /transaction-types should return list of types")
    void findAll_ShouldReturnListOfTypes_WhenTypesExist() throws Exception {
        TransactionType saved = fixtureHelper.persistTransactionType("Income");

        mockMvc.perform(get("/transaction-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.transactionTypeId == " + saved.getTransactionTypeId() + ")].transactionTypeName").value("Income"));
    }

    @Test
    @DisplayName("GET /transaction-types/{id} should return type when id exists")
    void findById_ShouldReturnType_WhenIdExists() throws Exception {
        TransactionType saved = fixtureHelper.persistTransactionType("Expense");

        mockMvc.perform(get("/transaction-types/{id}", saved.getTransactionTypeId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionTypeId").value(saved.getTransactionTypeId()))
                .andExpect(jsonPath("$.transactionTypeName").value("Expense"));
    }

    @Test
    @DisplayName("GET /transaction-types/{id} should return 400 when id does not exist")
    void findById_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/transaction-types/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /transaction-types should create type and persist in database")
    void save_ShouldPersistType_WhenPayloadIsValid() throws Exception {
        TransactionTypeRequestBody request = new TransactionTypeRequestBody("Transfer");

        mockMvc.perform(post("/transaction-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionTypeId").isNumber())
                .andExpect(jsonPath("$.transactionTypeName").value("Transfer"));

        assertThat(transactionTypeRepository.findAll())
                .filteredOn(t -> t.getTransactionTypeName().equals("Transfer"))
                .hasSize(1);
    }

    @Test
    @DisplayName("POST /transaction-types should return 400 when name is empty")
    void save_ShouldReturn400_WhenNameIsEmpty() throws Exception {
        TransactionTypeRequestBody invalidRequest = new TransactionTypeRequestBody("");

        mockMvc.perform(post("/transaction-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /transaction-types/{id} should update existing type")
    void update_ShouldUpdateType_WhenPayloadIsValid() throws Exception {
        TransactionType saved = fixtureHelper.persistTransactionType("Old Type");
        TransactionTypeRequestBody updateRequest = new TransactionTypeRequestBody("New Type");

        mockMvc.perform(put("/transaction-types/{id}", saved.getTransactionTypeId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNoContent());

        TransactionType updated = transactionTypeRepository.findById(saved.getTransactionTypeId()).orElseThrow();
        assertThat(updated.getTransactionTypeName()).isEqualTo("New Type");
    }

    @Test
    @DisplayName("PUT /transaction-types/{id} should return 400 when id does not exist")
    void update_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        TransactionTypeRequestBody updateRequest = new TransactionTypeRequestBody("New Type");

        mockMvc.perform(put("/transaction-types/{id}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /transaction-types/{id} should remove type")
    void delete_ShouldRemoveType_WhenIdExists() throws Exception {
        TransactionType saved = fixtureHelper.persistTransactionType("To Delete");

        mockMvc.perform(delete("/transaction-types/{id}", saved.getTransactionTypeId()))
                .andExpect(status().isNoContent());

        assertThat(transactionTypeRepository.findById(saved.getTransactionTypeId())).isEmpty();
    }

    @Test
    @DisplayName("DELETE /transaction-types/{id} should return 400 when id does not exist")
    void delete_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(delete("/transaction-types/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }
}
