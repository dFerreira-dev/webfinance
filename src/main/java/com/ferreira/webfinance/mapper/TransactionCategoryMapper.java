package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.TransactionCategoryRequestBody;
import com.ferreira.webfinance.entity.TransactionCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionCategoryMapper {

    TransactionCategory toTransactionCategory(TransactionCategoryRequestBody transactionCategoryRequestBody);
}
