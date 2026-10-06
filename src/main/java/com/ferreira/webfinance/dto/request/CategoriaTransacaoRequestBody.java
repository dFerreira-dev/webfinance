package com.ferreira.webfinance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaTransacaoRequestBody {
    @NotEmpty(message = "The category name (nomeCategoria) cannot be empty or null")
    private String nomeCategoria;
}
