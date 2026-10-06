package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.TransacaoResquestBody;
import com.ferreira.webfinance.entity.Transacao;
import com.ferreira.webfinance.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService transacaoService;

    @GetMapping
    public ResponseEntity<List<Transacao>> listAll() {

        return ResponseEntity.ok(transacaoService.listAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> findById(@PathVariable Long id) {

        return ResponseEntity.ok(transacaoService.findByIdOrThrowBadRequestException(id));

    }

    @PostMapping
    public ResponseEntity<Transacao> save(@RequestBody TransacaoResquestBody transacaoResquestBody) {
        return ResponseEntity.ok(transacaoService.save(transacaoResquestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody TransacaoResquestBody transacaoResquestBody) {
        transacaoService.update(id, transacaoResquestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        transacaoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
