package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.TransactionCategoryRequestBody;
import com.ferreira.webfinance.entity.TransactionCategory;
import com.ferreira.webfinance.service.TransactionCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction-categories")
@RequiredArgsConstructor
public class TransactionCategoryController {

    private final TransactionCategoryService transactionCategoryService;

    @GetMapping
    public ResponseEntity<List<TransactionCategory>> findAll() {
        return ResponseEntity.ok(transactionCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionCategory> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(transactionCategoryService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<TransactionCategory> save(@RequestBody TransactionCategoryRequestBody transactionCategoryRequestBody) {
        return ResponseEntity.ok(transactionCategoryService.save(transactionCategoryRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id,
                                       @RequestBody TransactionCategoryRequestBody transactionCategoryRequestBody) {
        transactionCategoryService.update(id, transactionCategoryRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        transactionCategoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
