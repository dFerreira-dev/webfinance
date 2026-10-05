package com.ferreira.webfinance.service;

import com.ferreira.webfinance.entity.Transacao;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;

    public List<Transacao> listAll(){
        return transacaoRepository.findAll();
    }

    public Transacao findByIdOrThrowBadRequestException(long id) {
        return transacaoRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transacao Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public Transacao save(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    // MUST IMPLEMENT POST REQUEST BODY
    void update(Transacao transacao) {
        transacaoRepository.save(transacao);
    }

    void delete(long id) {
        transacaoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
