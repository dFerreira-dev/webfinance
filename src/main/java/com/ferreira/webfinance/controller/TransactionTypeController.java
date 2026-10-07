package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.TransactionTypeRequestBody;
import com.ferreira.webfinance.entity.TransactionType;
import com.ferreira.webfinance.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction-types")
@RequiredArgsConstructor
public class TransactionTypeController {
    private final TransactionTypeService transactionTypeService;

    @GetMapping
    public ResponseEntity<List<TransactionType>> findAll() {
        return ResponseEntity.ok(transactionTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionType> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(transactionTypeService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<TransactionType> save(@RequestBody TransactionTypeRequestBody transactionTypeRequestBody) {
        return ResponseEntity.ok(transactionTypeService.save(transactionTypeRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id,
                                       @RequestBody TransactionTypeRequestBody transactionTypeRequestBody) {
        transactionTypeService.update(id, transactionTypeRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        transactionTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
