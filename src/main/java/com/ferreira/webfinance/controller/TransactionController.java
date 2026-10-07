package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.TransactionRequestBody;
import com.ferreira.webfinance.entity.Transaction;
import com.ferreira.webfinance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> listAll() {

        return ResponseEntity.ok(transactionService.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {

        return ResponseEntity.ok(transactionService.findByIdOrThrowBadRequestException(id));

    }

    @PostMapping
    public ResponseEntity<Transaction> save(@RequestBody TransactionRequestBody transactionRequestBody) {
        return ResponseEntity.ok(transactionService.save(transactionRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody TransactionRequestBody transactionRequestBody) {
        transactionService.update(id, transactionRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        transactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
