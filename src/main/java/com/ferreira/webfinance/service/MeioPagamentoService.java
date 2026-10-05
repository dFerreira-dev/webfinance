package com.ferreira.webfinance.service;

import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.MeioPagamento;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.repository.MeioPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeioPagamentoService {

    private final MeioPagamentoRepository meioPagamentoRepository;

    public List<MeioPagamento> listAll(){
        return meioPagamentoRepository.findAll();
    }

    public MeioPagamento findByIdOrThrowBadRequestException(long id) {
        return meioPagamentoRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transacao Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public MeioPagamento save(MeioPagamento meioPagamento) {
        return meioPagamentoRepository.save(meioPagamento);
    }

    // MUST IMPLEMENT POST REQUEST BODY
    void update(MeioPagamento meioPagamento) {
        meioPagamentoRepository.save(meioPagamento);
    }

    void delete(long id) {
        meioPagamentoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
