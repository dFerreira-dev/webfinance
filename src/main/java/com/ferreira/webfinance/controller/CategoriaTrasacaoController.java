package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.CategoriaTransacaoRequestBody;
import com.ferreira.webfinance.entity.CategoriaTransacao;
import com.ferreira.webfinance.service.CategoriaTransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias-transacoes")
@RequiredArgsConstructor
public class CategoriaTrasacaoController {

    private final CategoriaTransacaoService categoriaTransacaoService;

    @GetMapping
    public ResponseEntity<List<CategoriaTransacao>> findAll() {
        return ResponseEntity.ok(categoriaTransacaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaTransacao> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(categoriaTransacaoService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaTransacao> save(@RequestBody CategoriaTransacaoRequestBody categoriaTransacaoRequestBody) {
        return ResponseEntity.ok(categoriaTransacaoService.save(categoriaTransacaoRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id,
                                       @RequestBody CategoriaTransacaoRequestBody categoriaTransacaoRequestBody) {
        categoriaTransacaoService.update(id, categoriaTransacaoRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        categoriaTransacaoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
