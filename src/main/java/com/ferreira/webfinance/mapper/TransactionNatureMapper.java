package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.TransactionNatureRequestBody;
import com.ferreira.webfinance.entity.TransactionNature;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionNatureMapper {

    TransactionNature toTransactionNature(TransactionNatureRequestBody transactionNatureRequestBody);
}
