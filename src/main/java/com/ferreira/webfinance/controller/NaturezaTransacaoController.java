package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.ContaRequestBody;
import com.ferreira.webfinance.dto.request.NaturezaTransacaoRequestBody;
import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.NaturezaTransacao;
import com.ferreira.webfinance.service.ContaService;
import com.ferreira.webfinance.service.NaturezaTransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/naturezas-transacao")
@RequiredArgsConstructor
public class NaturezaTransacaoController {
    private final NaturezaTransacaoService naturezaTransacaoService;

    @GetMapping
    public ResponseEntity<List<NaturezaTransacao>> findAll() {
        return ResponseEntity.ok(naturezaTransacaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NaturezaTransacao> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(naturezaTransacaoService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<NaturezaTransacao> save(@RequestBody NaturezaTransacaoRequestBody naturezaTransacaoRequestBody) {
        return ResponseEntity.ok(naturezaTransacaoService.save(naturezaTransacaoRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id,
                                       @RequestBody NaturezaTransacaoRequestBody naturezaTransacaoRequestBody) {
        naturezaTransacaoService.update(id, naturezaTransacaoRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        naturezaTransacaoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
