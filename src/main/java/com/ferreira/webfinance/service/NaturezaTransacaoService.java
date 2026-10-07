package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.MeioPagamentoRequestBody;
import com.ferreira.webfinance.dto.request.NaturezaTransacaoRequestBody;
import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.MeioPagamento;
import com.ferreira.webfinance.entity.NaturezaTransacao;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.MeioPagamentoMapper;
import com.ferreira.webfinance.mapper.NaturezaTransacaoMapper;
import com.ferreira.webfinance.repository.MeioPagamentoRepository;
import com.ferreira.webfinance.repository.NaturezaTransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NaturezaTransacaoService {
    private final NaturezaTransacaoRepository naturezaTransacaoRepository;
    private final NaturezaTransacaoMapper naturezaTransacaoMapper;

    public List<NaturezaTransacao> findAll(){
        /*
         * Returns a list of all nature transactions (natureza transacao)
         */
        return naturezaTransacaoRepository.findAll();
    }

    public NaturezaTransacao findByIdOrThrowBadRequestException(long id) {
        /*
         * Returns an transaction nature (natureza transacao) if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return naturezaTransacaoRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transaction Nature (natureza transacao) Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public NaturezaTransacao save(NaturezaTransacaoRequestBody naturezaTransacaoRequestBody) {
        /*
         * save transaction nature (natureza transacao)
         * - This method expect an NaturezaTransacaoRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new transaction nature
         * */
        return naturezaTransacaoRepository.save(
                naturezaTransacaoMapper.toNaturezaTransacao(naturezaTransacaoRequestBody));
    }

    // MUST IMPLEMENT POST REQUEST BODY
    public void update(long id, NaturezaTransacaoRequestBody naturezaTransacaoRequestBody) {
        /*
         * update transaction nature (natureza transacao)
         * - This method firstly try to find an original nature method by id and returns it;
         * - If it's found, then a temporary nature method is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original nature
         * - Finally, the repository saves the nature updated
         * */
        NaturezaTransacao naturezaTransacao = findByIdOrThrowBadRequestException(id);

        NaturezaTransacao updatedData = naturezaTransacaoMapper.toNaturezaTransacao(naturezaTransacaoRequestBody);
        naturezaTransacao.setNomeNaturezaTransacao(updatedData.getNomeNaturezaTransacao());

        naturezaTransacaoRepository.save(naturezaTransacao);
    }

    public void delete(long id) {

        /*
         * Delete existing transaction nature (natureza transacao) using id
         * If id not exists, it throws a BadRequestExcepetion
         * */
        naturezaTransacaoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
