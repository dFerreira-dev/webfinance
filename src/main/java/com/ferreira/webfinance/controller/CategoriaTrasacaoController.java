package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.entity.CategoriaTransacao;
import com.ferreira.webfinance.service.CategoriaTransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria-transacao")
@RequiredArgsConstructor
public class CategoriaTrasacaoController {

    private final CategoriaTransacaoService categoriaTransacaoService;

    @GetMapping
    public List<CategoriaTransacao> findAll() {
        return categoriaTransacaoService.findAll();
    }

    @GetMapping("/{id}")
    public CategoriaTransacao findById(@PathVariable("id") long id) {
        return categoriaTransacaoService.findByIdOrThrowBadRequestException(id);
    }

    @PostMapping
    public void save(@RequestBody CategoriaTransacao categoriaTransacao) {
        categoriaTransacaoService.save(categoriaTransacao);
    }

    @DeleteMapping
    public void delete(@PathVariable("id") long id) {
        categoriaTransacaoService.delete(id);
    }
}
