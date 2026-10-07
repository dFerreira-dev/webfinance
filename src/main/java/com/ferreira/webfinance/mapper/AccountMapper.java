package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.AccountRequestBody;
import com.ferreira.webfinance.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {

    public static final AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    public abstract Account toAccount(AccountRequestBody accountRequestBody);
}
