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
         * Returns a transaction category (categoria transacao) if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return categoriaTransacaoRepository.findById(id).
                orElseThrow(() -> new BadRequestException("CategoriaTransacao Not Found"));
    }

    public CategoriaTransacao save(CategoriaTransacaoRequestBody categoriaTransacaoRequestBody) {
        /*
         * save transaction category (categoria transacao)
         * - This method expect a CategoriaTransacaoRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new CategoriaTransacao
         * */
        return categoriaTransacaoRepository.save(
                categoriaTransacaoMapper
                .toCategoriaTransacao(categoriaTransacaoRequestBody));
    }


    public void update(long id, CategoriaTransacaoRequestBody categoriaTransacaoRequestBody) {

        /*
         *update transaction category (categoria transacao)
         * - This method firstly try to find an original category by id and returns it;
         * - If it's found, then a temporary category is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original category
         * - Finally, the repository saves the category updated
         * */

        CategoriaTransacao categoriaTransacao = findByIdOrThrowBadRequestException(id);

        CategoriaTransacao updatedData  = categoriaTransacaoMapper.toCategoriaTransacao(categoriaTransacaoRequestBody);
        categoriaTransacao.setNomeCategoria(updatedData.getNomeCategoria());

        categoriaTransacaoRepository.save(categoriaTransacao);
    }

    public void delete(long id) {
        /*
         * Delete existing transaction category (categoria transacao) using id
         * If id not exists, it throws a BadRequestExcepetion
         * */
        categoriaTransacaoRepository.delete(findByIdOrThrowBadRequestException(id));
    }


}
