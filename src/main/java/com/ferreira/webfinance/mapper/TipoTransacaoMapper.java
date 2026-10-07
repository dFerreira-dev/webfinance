package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.ContaRequestBody;
import com.ferreira.webfinance.dto.request.TipoTransacaoRequestBody;
import com.ferreira.webfinance.entity.Conta;
import com.ferreira.webfinance.entity.TipoTransacao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class TipoTransacaoMapper {

    public static final TipoTransacaoMapper INSTANCE = Mappers.getMapper(TipoTransacaoMapper.class);

    public abstract TipoTransacao toTipoTransacao(TipoTransacaoRequestBody tipoTransacaoRequestBody);
}
