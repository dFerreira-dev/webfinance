package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.TransactionNatureRequestBody;
import com.ferreira.webfinance.entity.TransactionNature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionNatureMapper {

    @Mapping(source = "trasactionNatureName", target = "transactionNatureName")
    TransactionNature toTransactionNature(TransactionNatureRequestBody transactionNatureRequestBody);
}
