package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.NaturezaTransacaoRequestBody;
import com.ferreira.webfinance.dto.request.TipoTransacaoRequestBody;
import com.ferreira.webfinance.entity.NaturezaTransacao;
import com.ferreira.webfinance.entity.TipoTransacao;
import com.ferreira.webfinance.service.NaturezaTransacaoService;
import com.ferreira.webfinance.service.TipoTransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-transacao")
@RequiredArgsConstructor
public class TipoTransacaoController {
    private final TipoTransacaoService tipoTransacaoService;

    @GetMapping
    public ResponseEntity<List<TipoTransacao>> findAll() {
        return ResponseEntity.ok(tipoTransacaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTransacao> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(tipoTransacaoService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<TipoTransacao> save(@RequestBody TipoTransacaoRequestBody tipoTransacaoRequestBody) {
        return ResponseEntity.ok(tipoTransacaoService.save(tipoTransacaoRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id,
                                       @RequestBody TipoTransacaoRequestBody tipoTransacaoRequestBody) {
        tipoTransacaoService.update(id, tipoTransacaoRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        tipoTransacaoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
