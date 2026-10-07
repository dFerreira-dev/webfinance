package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.TransactionNatureRequestBody;
import com.ferreira.webfinance.entity.TransactionNature;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class TransactionNatureMapper {

    public static final TransactionNatureMapper INSTANCE = Mappers.getMapper(TransactionNatureMapper.class);

    public abstract TransactionNature toTransactionNature(TransactionNatureRequestBody transactionNatureRequestBody);
}
