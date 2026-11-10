package com.ferreira.webfinance.integration.controller;

import com.ferreira.webfinance.dto.request.PaymentMethodRequestBody;
import com.ferreira.webfinance.entity.PaymentMethod;
import com.ferreira.webfinance.integration.config.BaseIntegrationTest;
import com.ferreira.webfinance.repository.PaymentMethodRepository;
import com.ferreira.webfinance.util.fixture.DatabaseFixtureHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentMethodControllerIT extends BaseIntegrationTest {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private DatabaseFixtureHelper fixtureHelper;

    @Test
    @DisplayName("GET /payment-methods should return list of payment methods")
    void findAll_ShouldReturnListOfPaymentMethods_WhenPaymentMethodsExist() throws Exception {
        PaymentMethod saved = fixtureHelper.persistPaymentMethod("Credit Card");

        mockMvc.perform(get("/payment-methods"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.paymentMethodID == " + saved.getPaymentMethodID() + ")].paymentMethodName").value("Credit Card"));
    }

    @Test
    @DisplayName("GET /payment-methods/{id} should return payment method when id exists")
    void findById_ShouldReturnPaymentMethod_WhenIdExists() throws Exception {
        PaymentMethod saved = fixtureHelper.persistPaymentMethod("Debit Card");

        mockMvc.perform(get("/payment-methods/{id}", saved.getPaymentMethodID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentMethodID").value(saved.getPaymentMethodID()))
                .andExpect(jsonPath("$.paymentMethodName").value("Debit Card"));
    }

    @Test
    @DisplayName("GET /payment-methods/{id} should return 400 when id does not exist")
    void findById_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/payment-methods/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /payment-methods should create payment method and persist in database")
    void save_ShouldPersistPaymentMethod_WhenPayloadIsValid() throws Exception {
        PaymentMethodRequestBody request = new PaymentMethodRequestBody("PIX");

        mockMvc.perform(post("/payment-methods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentMethodID").isNumber())
                .andExpect(jsonPath("$.paymentMethodName").value("PIX"));

        assertThat(paymentMethodRepository.findAll())
                .filteredOn(p -> p.getPaymentMethodName().equals("PIX"))
                .hasSize(1);
    }

    @Test
    @DisplayName("POST /payment-methods should return 400 when paymentMethodName is empty")
    void save_ShouldReturn400_WhenPaymentMethodNameIsEmpty() throws Exception {
        PaymentMethodRequestBody invalidRequest = new PaymentMethodRequestBody("");

        mockMvc.perform(post("/payment-methods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /payment-methods/{id} should update existing payment method")
    void update_ShouldUpdatePaymentMethod_WhenPayloadIsValid() throws Exception {
        PaymentMethod saved = fixtureHelper.persistPaymentMethod("Old PIX");
        PaymentMethodRequestBody updateRequest = new PaymentMethodRequestBody("New PIX");

        mockMvc.perform(put("/payment-methods/{id}", saved.getPaymentMethodID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNoContent());

        PaymentMethod updated = paymentMethodRepository.findById(saved.getPaymentMethodID()).orElseThrow();
        assertThat(updated.getPaymentMethodName()).isEqualTo("New PIX");
    }

    @Test
    @DisplayName("PUT /payment-methods/{id} should return 400 when id does not exist")
    void update_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        PaymentMethodRequestBody updateRequest = new PaymentMethodRequestBody("New Name");

        mockMvc.perform(put("/payment-methods/{id}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /payment-methods/{id} should remove payment method")
    void delete_ShouldRemovePaymentMethod_WhenIdExists() throws Exception {
        PaymentMethod saved = fixtureHelper.persistPaymentMethod("To Delete");

        mockMvc.perform(delete("/payment-methods/{id}", saved.getPaymentMethodID()))
                .andExpect(status().isNoContent());

        assertThat(paymentMethodRepository.findById(saved.getPaymentMethodID())).isEmpty();
    }

    @Test
    @DisplayName("DELETE /payment-methods/{id} should return 400 when id does not exist")
    void delete_ShouldReturn400_WhenIdDoesNotExist() throws Exception {
        mockMvc.perform(delete("/payment-methods/{id}", 99999L))
                .andExpect(status().isBadRequest());
    }
}
