package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.NaturezaTransacaoRequestBody;
import com.ferreira.webfinance.dto.request.TipoTransacaoRequestBody;
import com.ferreira.webfinance.entity.NaturezaTransacao;
import com.ferreira.webfinance.entity.TipoTransacao;
import com.ferreira.webfinance.entity.Transacao;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.TipoTransacaoMapper;
import com.ferreira.webfinance.repository.TipoTransacaoRepository;
import com.ferreira.webfinance.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoTransacaoService {
    private final TipoTransacaoRepository tipoTransacaoRepository;
    private final TipoTransacaoMapper tipoTransacaoMapper;

    public List<TipoTransacao> findAll(){
        /*
         * Returns a list of all transaction types (tipo transacao)
         */
        return tipoTransacaoRepository.findAll();
    }

    public TipoTransacao findByIdOrThrowBadRequestException(long id) {
        /*
         * Returns an transaction type (tipo transacao) if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return tipoTransacaoRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transaction Nature (natureza transacao) Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public TipoTransacao save(TipoTransacaoRequestBody tipoTransacaoRequestBody) {
        /*
         * save transaction type (tipo transacao)
         * - This method expect an TipoTransacaoRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new transaction type
         * */
        return tipoTransacaoRepository.save(
                tipoTransacaoMapper.toTipoTransacao(tipoTransacaoRequestBody));
    }

    public void update(long id, TipoTransacaoRequestBody tipoTransacaoRequestBody) {
        /*
         * update transaction type (tipo transacao)
         * - This method firstly try to find an original transaction type by id and returns it;
         * - If it's found, then a temporary transaction type is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original type
         * - Finally, the repository saves the transaction type
         * */
        TipoTransacao tipoTransacao = findByIdOrThrowBadRequestException(id);

        TipoTransacao updatedData = tipoTransacaoMapper.toTipoTransacao(tipoTransacaoRequestBody);
        tipoTransacao.setNomeTipoTransacao(updatedData.getNomeTipoTransacao());

        tipoTransacaoRepository.save(tipoTransacao);
    }

    public void delete(long id) {

        /*
         * Delete existing transaction type (tipo transacao) using id
         * If id not exists, it throws a BadRequestExcepetion
         * */
        tipoTransacaoRepository.delete(findByIdOrThrowBadRequestException(id));
    }
}
