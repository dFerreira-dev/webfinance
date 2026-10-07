package com.ferreira.webfinance.mapper;

import com.ferreira.webfinance.dto.request.AccountRequestBody;
import com.ferreira.webfinance.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toAccount(AccountRequestBody accountRequestBody);
}
