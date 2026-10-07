package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.ContaRequestBody;
import com.ferreira.webfinance.dto.request.NaturezaTransacaoRequestBody;
import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.NaturezaTransacao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class NaturezaTransacaoMapper {

    public static final NaturezaTransacaoMapper INSTANCE = Mappers.getMapper(NaturezaTransacaoMapper.class);

    public abstract NaturezaTransacao toNaturezaTransacao(NaturezaTransacaoRequestBody naturezaTransacaoRequestBody);
}
