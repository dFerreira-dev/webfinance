package com.ferreira.webfinance.integration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest // Initialize spring context for tests (controllers, services, repositories, beans, configs)
@AutoConfigureMockMvc // Spring autoconfigure MockMvc
@Transactional // Automatically rolls back database changes after each test method
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    //-----------------------
    // Dependencies injection
    //-----------------------

    //MockMcv allows to simulate HTTP requests to controllers
    @Autowired
    protected MockMvc mockMvc;

    //ObjectMapper allows convert Java objects to JSON (and vice versa)
    @Autowired
    protected ObjectMapper objectMapper;
}