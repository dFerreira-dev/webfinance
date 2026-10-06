package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.TransacaoResquestBody;
import com.ferreira.webfinance.entity.*;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final CategoriaTransacaoRepository categoriaTransacaoRepository;
    private final ContaRepository contaRepository;
    private final MeioPagamentoRepository meioPagamentoRepository;
    private final NaturezaTransacaoRepository naturezaTransacaoRepository;
    private final TipoTransacaoRepository tipoTransacaoRepository;
    private final TransacaoRepository transacaoRepository;

    //-------------------------------
    // PRIVATE/INTERNAL METHODS------
    //-------------------------------
    private Transacao buildTransacao(TransacaoResquestBody transacaoResquestBody) {

        //find Tipo Transacao
        TipoTransacao tipoTransacao = tipoTransacaoRepository.findById(transacaoResquestBody.getTipoTransacaoId())
                .orElseThrow(()-> new BadRequestException("Tipo Transacao Not Found"));

        //find Categoria Transacao
        CategoriaTransacao categoriaTransacao = transacaoResquestBody.getCategoriaTransacaoId() != null
                ? categoriaTransacaoRepository.findById(transacaoResquestBody.getCategoriaTransacaoId())
                .orElseThrow(() -> new BadRequestException("Categoria Transacao Not Found")): null;

        //find Natureza Transacao
        NaturezaTransacao naturezaTransacao = naturezaTransacaoRepository.findById(transacaoResquestBody.getNaturezaTransacaoId())
                .orElseThrow(()-> new BadRequestException("Categoria Transacao Not Found"));

        //find Meio Pagamento
        MeioPagamento meioPagamento = transacaoResquestBody.getMeioPagamentoId() != null
                ? meioPagamentoRepository.findById(transacaoResquestBody.getMeioPagamentoId())
                .orElseThrow(()-> new BadRequestException("Meio Pagamento Not Found")) : null;

        //find Conta
        Conta conta = contaRepository.findById(transacaoResquestBody.getContaId())
                .orElseThrow(()-> new BadRequestException("Conta Not Found"));

        //return Transacao built
        return Transacao.builder()
                .dataTransacao(transacaoResquestBody.getDataTransacao())
                .dataEfetivaPagamento(transacaoResquestBody.getDataEfetivaPagamento())
                .descricao(transacaoResquestBody.getDescricao())
                .tipoTransacao(tipoTransacao)
                .categoriaTransacao(categoriaTransacao)
                .naturezaTransacao(naturezaTransacao)
                .meioPagamento(meioPagamento)
                .conta(conta)
                .valor(transacaoResquestBody.getValor())
                .build();
    }

    //-------------------------------
    // CONTROLLER ACCESS METHODS-----
    //-------------------------------

    public List<Transacao> findAll(){
        /*
        * Returns a list of all transacoes (transactions)
        */
        return transacaoRepository.findAll();
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public Transacao findByIdOrThrowBadRequestException(long id) {

        /*
        * Returns a Transacao if the id is found
        * If isn't found, then returns a BadRequestExeption
        * */

        return transacaoRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Transacao Not Found"));
    }

    public void update(long id, TransacaoResquestBody transacaoResquestBody) {
        /*update Transacao
         * - This method firstly try to find an original Transacao by id en returns it;
         * - If it's found, then a temporary Transacao is created using request data (updated data)
         * - Then, the data of updatedDate is copied to the original Transacao
         * - Finally, the repository saves the Transacao updated
         * */

        Transacao transacao = findByIdOrThrowBadRequestException(id);

        Transacao updatedData  = buildTransacao(transacaoResquestBody);

        transacao.setDataTransacao(updatedData.getDataTransacao());
        transacao.setDataEfetivaPagamento(updatedData.getDataEfetivaPagamento());
        transacao.setDescricao(updatedData.getDescricao());
        transacao.setTipoTransacao(updatedData.getTipoTransacao());
        transacao.setCategoriaTransacao(updatedData.getCategoriaTransacao());
        transacao.setNaturezaTransacao(updatedData.getNaturezaTransacao());
        transacao.setMeioPagamento(updatedData.getMeioPagamento());
        transacao.setConta(updatedData.getConta());
        transacao.setValor(updatedData.getValor());
        transacaoRepository.save(transacao);

    }

    public Transacao save(TransacaoResquestBody transacaoResquestBody) {
        /*save Transacao
        * - This methods expect a TransacaoRequestBody
        * - Then it calls buildTransacao passing the request
        * - buildTransacao returns the Transacao
        * - repository saves new Transacao
        * */

        return transacaoRepository.save(buildTransacao(transacaoResquestBody));
    }

    public void delete(long id) {
        /*
        * Delete existing transacao (transaction) using id
        * If id not exists, it returns a BadRequestExcepetion
        * */
        transacaoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
