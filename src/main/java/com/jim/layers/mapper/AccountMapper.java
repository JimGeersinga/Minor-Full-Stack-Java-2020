package com.jim.layers.mapper;

import com.jim.layers.dao.Account;
import com.jim.layers.dto.AccountDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto mapToDestination(Account source);
    Collection<AccountDto> mapToDestination(Collection<Account> source);

    Account mapToSource(AccountDto destination);
    Collection<Account> mapToSource(Collection<AccountDto> destination);
}
