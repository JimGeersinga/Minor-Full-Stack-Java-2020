package com.jim.layers.service;

import com.jim.layers.dao.Account;
import com.jim.layers.dto.AccountDto;
import com.jim.layers.exception.AccountNotFoundException;
import com.jim.layers.helper.AccountNumberGenerator;
import com.jim.layers.mapper.AccountMapper;
import com.jim.layers.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public Collection<Account> getAll(){
        return accountRepository.findAll();
    }

    public Optional<Account> getById(long id){
        return accountRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public Account create(AccountDto acc){
        Account account = accountMapper.mapToSource(acc);

        String accountNumber = AccountNumberGenerator.generate();
        account.setAccountNumber(accountNumber);

        String iban = AccountNumberGenerator.generateIban("INGB", accountNumber);
        account.setIban(iban);

        return accountRepository.save(account);
    }

    @Transactional(readOnly = false)
    public void update(long id, AccountDto acc) throws AccountNotFoundException {
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        account.setAmount(acc.getAmount());
        account.setIsBlocked(acc.getIsBlocked());
        accountRepository.save(account);
    }

    @Transactional(readOnly = false)
    public void delete(long id) throws AccountNotFoundException {
        if(!accountRepository.existsById(id)){
            throw new AccountNotFoundException();
        }
        accountRepository.deleteById(id);
    }
}
