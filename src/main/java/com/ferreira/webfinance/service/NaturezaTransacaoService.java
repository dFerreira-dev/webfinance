package com.ferreira.webfinance.service;

import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.NaturezaTransacao;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.repository.NaturezaTransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NaturezaTransacaoService {

    private final NaturezaTransacaoRepository naturezaTransacaoRepository;

    public List<NaturezaTransacao> listAll(){
        return naturezaTransacaoRepository.findAll();
    }

    public NaturezaTransacao findByIdOrThrowBadRequestException(long id) {
        return naturezaTransacaoRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transacao Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public NaturezaTransacao save(NaturezaTransacao naturezaTransacao) {
        return naturezaTransacaoRepository.save(naturezaTransacao);
    }

    // MUST IMPLEMENT POST REQUEST BODY
    void update(NaturezaTransacao naturezaTransacao) {
        naturezaTransacaoRepository.save(naturezaTransacao);
    }

    void delete(long id) {
        naturezaTransacaoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
