package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.TransactionNatureRequestBody;
import com.ferreira.webfinance.entity.TransactionNature;
import com.ferreira.webfinance.service.TransactionNatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction-natures")
@RequiredArgsConstructor
public class TransactionNatureController {
    private final TransactionNatureService transactionNatureService;

    @GetMapping
    public ResponseEntity<List<TransactionNature>> findAll() {
        return ResponseEntity.ok(transactionNatureService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionNature> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(transactionNatureService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<TransactionNature> save(@RequestBody TransactionNatureRequestBody transactionNatureRequestBody) {
        return ResponseEntity.ok(transactionNatureService.save(transactionNatureRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id,
                                       @RequestBody TransactionNatureRequestBody transactionNatureRequestBody) {
        transactionNatureService.update(id, transactionNatureRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        transactionNatureService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
