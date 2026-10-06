package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.CategoriaTransacaoRequestBody;
import com.ferreira.webfinance.entity.CategoriaTransacao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CategoriaTransacaoMapper {

    public static final CategoriaTransacaoMapper INSTANCE = Mappers.getMapper(CategoriaTransacaoMapper.class);

    public abstract CategoriaTransacao toCategoriaTransacao(CategoriaTransacaoRequestBody categoriaTransacaoRequestBody);
}
