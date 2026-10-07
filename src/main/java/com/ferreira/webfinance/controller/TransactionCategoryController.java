package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.TransactionCategoryRequestBody;
import com.ferreira.webfinance.entity.TransactionCategory;
import com.ferreira.webfinance.service.CategoriaTransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction-categories")
@RequiredArgsConstructor
public class TransactionCategoryController {

    private final CategoriaTransacaoService categoriaTransacaoService;

    @GetMapping
    public ResponseEntity<List<TransactionCategory>> findAll() {
        return ResponseEntity.ok(categoriaTransacaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionCategory> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(categoriaTransacaoService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<TransactionCategory> save(@RequestBody TransactionCategoryRequestBody transactionCategoryRequestBody) {
        return ResponseEntity.ok(categoriaTransacaoService.save(transactionCategoryRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id,
                                       @RequestBody TransactionCategoryRequestBody transactionCategoryRequestBody) {
        categoriaTransacaoService.update(id, transactionCategoryRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        categoriaTransacaoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
