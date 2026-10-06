package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.CategoriaTransacaoRequestBody;
import com.ferreira.webfinance.entity.CategoriaTransacao;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.CategoriaTransacaoMapper;
import com.ferreira.webfinance.repository.CategoriaTransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaTransacaoService {

    private final CategoriaTransacaoRepository categoriaTransacaoRepository;
    private final CategoriaTransacaoMapper categoriaTransacaoMapper;

    public List<CategoriaTransacao> findAll(){
        /*
         * Returns a list of all categories (Categoria Transacao)
         */
        return categoriaTransacaoRepository.findAll();
    }

    public CategoriaTransacao findByIdOrThrowBadRequestException(long id) {
        /*
         * Returns a Categoria Transacao if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return categoriaTransacaoRepository.findById(id).
                orElseThrow(() -> new BadRequestException("CategoriaTransacao Not Found"));
    }

    public CategoriaTransacao save(CategoriaTransacaoRequestBody categoriaTransacaoRequestBody) {
        /*
         * save Transacao
         * - This methods expect a CategoriaTransacaoRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new CategoriaTransacao
         * */
        return categoriaTransacaoRepository.save(
                categoriaTransacaoMapper
                .toCategoriaTransacao(categoriaTransacaoRequestBody));
    }


    public void update(long id, CategoriaTransacaoRequestBody categoriaTransacaoRequestBody) {

        /*update Transacao
         * - This method firstly try to find an original CategoriaTransacao by id and returns it;
         * - If it's found, then a temporary CategoriaTransacao is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original categoriaTransacao
         * - Finally, the repository saves the categoriaTransacao updated
         * */

        CategoriaTransacao categoriaTransacao = findByIdOrThrowBadRequestException(id);

        CategoriaTransacao updatedData  = categoriaTransacaoMapper.toCategoriaTransacao(categoriaTransacaoRequestBody);
        categoriaTransacao.setNomeCategoria(updatedData.getNomeCategoria());

        categoriaTransacaoRepository.save(categoriaTransacao);
    }

    public void delete(long id) {
        /*
         * Delete existing Categoria Transacao using id
         * If id not exists, it returns a BadRequestExcepetion
         * */
        categoriaTransacaoRepository.delete(findByIdOrThrowBadRequestException(id));
    }


}
