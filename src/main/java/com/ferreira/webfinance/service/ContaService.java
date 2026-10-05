package com.ferreira.webfinance.service;

import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.TipoTransacao;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.repository.ContaRepository;
import com.ferreira.webfinance.repository.TipoTransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;

    public List<Conta> listAll(){
        return contaRepository.findAll();
    }

    public Conta findByIdOrThrowBadRequestException(long id) {
        return contaRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transacao Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public Conta save(Conta conta) {
        return contaRepository.save(conta);
    }

    // MUST IMPLEMENT POST REQUEST BODY
    void update(Conta conta) {
        contaRepository.save(conta);
    }

    void delete(long id) {
        contaRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
