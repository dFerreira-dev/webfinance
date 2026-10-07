package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.TransactionCategoryRequestBody;
import com.ferreira.webfinance.entity.TransactionCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class TransactionCategoryMapper {

    public static final TransactionCategoryMapper INSTANCE = Mappers.getMapper(TransactionCategoryMapper.class);

    public abstract TransactionCategory toTransactionCategory(TransactionCategoryRequestBody transactionCategoryRequestBody);
}
