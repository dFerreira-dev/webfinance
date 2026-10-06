package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.ContaRequestBody;
import com.ferreira.webfinance.dto.request.MeioPagamentoRequestBody;
import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.MeioPagamento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class MeioPagamentoMapper {

    public static final MeioPagamentoMapper INSTANCE = Mappers.getMapper(MeioPagamentoMapper.class);

    public abstract MeioPagamento toMeioPagamento(MeioPagamentoRequestBody meioPagamentoRequestBody);
}
