package com.ferreira.webfinance.service;

import com.ferreira.webfinance.entity.CategoriaTransacao;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.repository.CategoriaTransacaoRepository;

import java.util.List;

public class CategoriaTransacaoService {
    private CategoriaTransacaoRepository categoriaTransacaoRepository;

    public List<CategoriaTransacao> findAll(){
        return categoriaTransacaoRepository.findAll();
    }

    public CategoriaTransacao findByIdOrThrowBadRequestException(long id) {
        return categoriaTransacaoRepository.findById(id).
                orElseThrow(() -> new BadRequestException("CategoriaTransacao Not Found"));
    }

    public CategoriaTransacao save(CategoriaTransacao categoriaTransacao) {
        return categoriaTransacaoRepository.save(categoriaTransacao);
    }

    // MUST IMPLEMENT POST REQUEST BODY
    void update(CategoriaTransacao categoriaTransacao) {
        categoriaTransacaoRepository.save(categoriaTransacao);
    }

    void delete(long id) {
        categoriaTransacaoRepository.delete(findByIdOrThrowBadRequestException(id));
    }


}
