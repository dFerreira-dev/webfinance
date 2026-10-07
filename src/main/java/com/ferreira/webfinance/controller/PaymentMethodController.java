package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.PaymentMethodRequestBody;
import com.ferreira.webfinance.entity.PaymentMethod;
import com.ferreira.webfinance.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;


    @GetMapping
    public ResponseEntity<List<PaymentMethod>> findAll() {

        return ResponseEntity.ok(paymentMethodService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(paymentMethodService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<PaymentMethod> save(@RequestBody PaymentMethodRequestBody paymentMethodRequestBody) {
        return ResponseEntity.ok(paymentMethodService.save(paymentMethodRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody PaymentMethodRequestBody paymentMethodRequestBody) {
        paymentMethodService.update(id, paymentMethodRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        paymentMethodService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
