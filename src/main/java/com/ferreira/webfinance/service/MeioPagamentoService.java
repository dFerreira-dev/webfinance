package com.ferreira.webfinance.service;

import com.ferreira.webfinance.dto.request.MeioPagamentoRequestBody;
import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.MeioPagamento;
import com.ferreira.webfinance.exception.BadRequestException;
import com.ferreira.webfinance.mapper.MeioPagamentoMapper;
import com.ferreira.webfinance.repository.MeioPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeioPagamentoService {
    private final MeioPagamentoRepository meioPagamentoRepository;
    private final MeioPagamentoMapper meioPagamentoMapper;

    public List<MeioPagamento> findAll(){
        /*
         * Returns a list of all payments methods (meios pagamentos)
         */
        return meioPagamentoRepository.findAll();
    }

    public MeioPagamento findByIdOrThrowBadRequestException(long id) {
        /*
         * Returns an payments method (meio pagamento) if the id is found
         * If isn't found, then throws a BadRequestExeption
         * */
        return meioPagamentoRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Payment Method (meio pagamento) Not Found"));
    }

    //  RESERVED SPACE FOR GET's METHODS

    // RESERVED SPACE FOR GET's METHODS

    public MeioPagamento save(MeioPagamentoRequestBody meioPagamentoRequestBody) {
        /*
         * save payment method (meio pagamento)
         * - This method expect an meioPagamentoRequestBody
         * - Then it calls the Mapper to convert the DTO to Entity
         * - repository saves new payment method
         * */
        return meioPagamentoRepository.save(meioPagamentoMapper.toMeioPagamento(meioPagamentoRequestBody));
    }

    // MUST IMPLEMENT POST REQUEST BODY
    public void update(long id, MeioPagamentoRequestBody meioPagamentoRequestBody) {
        /*
         * update payment method (meio pagamento)
         * - This method firstly try to find an original payment method by id and returns it;
         * - If it's found, then a temporary payment method is created using request data (updated data)
         * - Then, the data of updatedData is copied to the original payment method
         * - Finally, the repository saves the payment method updated
         * */
        MeioPagamento meioPagamento = findByIdOrThrowBadRequestException(id);

        MeioPagamento updatedData = meioPagamentoMapper.toMeioPagamento(meioPagamentoRequestBody);
        meioPagamento.setNomeMeioPagamento(updatedData.getNomeMeioPagamento());

        meioPagamentoRepository.save(meioPagamento);
    }

    public void delete(long id) {

        /*
         * Delete existing payment method (meio pagamento) using id
         * If id not exists, it throws a BadRequestExcepetion
         * */
        meioPagamentoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
