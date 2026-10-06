package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.ContaRequestBody;
import com.ferreira.webfinance.entity.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ContaMapper {

    public static final ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);

    public abstract Conta toConta(ContaRequestBody contaRequestBody);
}
