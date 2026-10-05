package com.ferreira.webfinance.service;

import com.ferreira.webfinance.entity.TipoTransacao;
import com.ferreira.webfinance.entity.Transacao;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.repository.TipoTransacaoRepository;
import com.ferreira.webfinance.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoTransacaoService {

    private final TipoTransacaoRepository tipoTransacaoRepository;

    public List<TipoTransacao> listAll(){
        return tipoTransacaoRepository.findAll();
    }

    public TipoTransacao findByIdOrThrowBadRequestException(long id) {
        return tipoTransacaoRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transacao Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public TipoTransacao save(TipoTransacao tipoTransacao) {
        return tipoTransacaoRepository.save(tipoTransacao);
    }

    // MUST IMPLEMENT POST REQUEST BODY
    void update(TipoTransacao tipoTransacao) {
        tipoTransacaoRepository.save(tipoTransacao);
    }

    void delete(long id) {
        tipoTransacaoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
