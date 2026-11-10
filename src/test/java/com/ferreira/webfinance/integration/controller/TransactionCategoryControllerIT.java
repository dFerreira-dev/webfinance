package com.ferreira.webfinance.integration.controller;

import com.ferreira.webfinance.dto.request.TransactionCategoryRequestBody;
import com.ferreira.webfinance.entity.TransactionCategory;
import com.ferreira.webfinance.integration.config.BaseIntegrationTest;
import com.ferreira.webfinance.repository.TransactionCategoryRepository;
import com.ferreira.webfinance.util.fixture.DatabaseFixtureHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionCategoryControllerIT extends BaseIntegrationTest {

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @Autowired
    private DatabaseFixtureHelper fixtureHelper;

    @Test
    @DisplayName("GET /transaction-categories should return list of categories")
    void findAll_ShouldReturnListOfCategories_WhenCategoriesExist() throws Exception {
        TransactionCategory saved = fixtureHelper.persistTransactionCategory("Education");

        mockMvc.perform(get("/transaction-categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.transactionCategoryId == " + saved.getTransactionCategoryId() + ")].transactionCategoryName").value("Education"));
    }

    @Test
    @DisplayName("GET /transaction-categories/{id} should return category when id exists")
    void findById_ShouldReturnCategory_WhenIdExists() throws Exception {
        TransactionCategory saved = fixtureHelper.persistTransactionCategory("Health");

        mockMvc.perform(get("/transaction-categories/{id}", saved.getTransactionCategoryId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionCategoryId").value(saved.getTransactionCategoryId()))
                .andExpect(jsonPath("$.transactionCategoryName").value("Health"));
    }

    @Test
    @DisplayName("GET /transaction-categories/{id} should return 400 when id does not exist")
    void findById_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/transaction-categories/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /transaction-categories should create category and persist in database")
    void save_ShouldPersistCategory_WhenPayloadIsValid() throws Exception {
        TransactionCategoryRequestBody request = new TransactionCategoryRequestBody("Groceries");

        mockMvc.perform(post("/transaction-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionCategoryId").isNumber())
                .andExpect(jsonPath("$.transactionCategoryName").value("Groceries"));

        assertThat(transactionCategoryRepository.findAll())
                .filteredOn(c -> c.getTransactionCategoryName().equals("Groceries"))
                .hasSize(1);
    }

    @Test
    @DisplayName("POST /transaction-categories should return 400 when transactionCategoryName is empty")
    void save_ShouldReturn400_WhenTransactionCategoryNameIsEmpty() throws Exception {
        TransactionCategoryRequestBody invalidRequest = new TransactionCategoryRequestBody("");

        mockMvc.perform(post("/transaction-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /transaction-categories/{id} should update existing category")
    void update_ShouldUpdateCategory_WhenPayloadIsValid() throws Exception {
        TransactionCategory saved = fixtureHelper.persistTransactionCategory("Old Leisure");
        TransactionCategoryRequestBody updateRequest = new TransactionCategoryRequestBody("New Leisure");

        mockMvc.perform(put("/transaction-categories/{id}", saved.getTransactionCategoryId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNoContent());

        TransactionCategory updated = transactionCategoryRepository.findById(saved.getTransactionCategoryId()).orElseThrow();
        assertThat(updated.getTransactionCategoryName()).isEqualTo("New Leisure");
    }

    @Test
    @DisplayName("PUT /transaction-categories/{id} should return 400 when id does not exist")
    void update_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        TransactionCategoryRequestBody updateRequest = new TransactionCategoryRequestBody("New Name");

        mockMvc.perform(put("/transaction-categories/{id}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /transaction-categories/{id} should remove category")
    void delete_ShouldRemoveCategory_WhenIdExists() throws Exception {
        TransactionCategory saved = fixtureHelper.persistTransactionCategory("To Delete");

        mockMvc.perform(delete("/transaction-categories/{id}", saved.getTransactionCategoryId()))
                .andExpect(status().isNoContent());

        assertThat(transactionCategoryRepository.findById(saved.getTransactionCategoryId())).isEmpty();
    }

    @Test
    @DisplayName("DELETE /transaction-categories/{id} should return 400 when id does not exist")
    void delete_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(delete("/transaction-categories/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }
}
