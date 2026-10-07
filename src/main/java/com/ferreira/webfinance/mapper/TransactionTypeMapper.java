package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.TransactionTypeRequestBody;
import com.ferreira.webfinance.entity.TransactionType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionTypeMapper {

    TransactionType toTransactionType(TransactionTypeRequestBody transactionTypeRequestBody);
}
