package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.MeioPagamentoRequestBody;
import com.ferreira.webfinance.entity.MeioPagamento;
import com.ferreira.webfinance.service.MeioPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meios-pagamento")
@RequiredArgsConstructor
public class MeioPagamentoController {
    private final MeioPagamentoService meioPagamentoService;


    @GetMapping
    public ResponseEntity<List<MeioPagamento>> findAll() {

        return ResponseEntity.ok(meioPagamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeioPagamento> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(meioPagamentoService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<MeioPagamento> save(@RequestBody MeioPagamentoRequestBody meioPagamentoRequestBody) {
        return ResponseEntity.ok(meioPagamentoService.save(meioPagamentoRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody MeioPagamentoRequestBody meioPagamentoRequestBody) {
        meioPagamentoService.update(id, meioPagamentoRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        meioPagamentoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
