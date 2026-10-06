package com.ferreira.webfinance.controller;

import com.ferreira.webfinance.dto.request.ContaRequestBody;
import com.ferreira.webfinance.dto.request.TransacaoResquestBody;
import com.ferreira.webfinance.entity.CategoriaTransacao;
import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.Transacao;
import com.ferreira.webfinance.mapper.ContaMapper;
import com.ferreira.webfinance.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService contaService;

    @GetMapping
    public ResponseEntity<List<Conta>> findAll() {
        return ResponseEntity.ok(contaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> findById(@PathVariable("id") long id) {
        return ResponseEntity.ok(contaService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<Conta> save(@RequestBody ContaRequestBody contaRequestBody) {
        return ResponseEntity.ok(contaService.save(contaRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody ContaRequestBody contaRequestBody) {
        contaService.update(id, contaRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        contaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
