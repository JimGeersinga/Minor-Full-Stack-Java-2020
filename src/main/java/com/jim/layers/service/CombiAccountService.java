package com.jim.layers.service;

import com.jim.layers.dao.Account;
import com.jim.layers.dao.CombiAccount;
import com.jim.layers.dto.CombiAccountDto;
import com.jim.layers.exception.AccountNotFoundException;
import com.jim.layers.helper.AccountNumberGenerator;
import com.jim.layers.mapper.CombiAccountMapper;
import com.jim.layers.repository.AccountRepository;
import com.jim.layers.repository.CombiAccountRepository;
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
public class CombiAccountService {
    private final CombiAccountRepository combiAccountRepository;
    private final AccountRepository accountRepository;
    private final CombiAccountMapper combiAccountMapper;

    public Collection<CombiAccount> getAll(){
        return combiAccountRepository.findAll();
    }

    public Optional<CombiAccount> getById(long id){
        return combiAccountRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public CombiAccount create(CombiAccountDto acc){
        CombiAccount account = combiAccountMapper.mapToSource(acc);

        account.setAccountA(createSubAccount(account.getAccountA()));
        account.setAccountB(createSubAccount(account.getAccountB()));

        return combiAccountRepository.save(account);
    }

    @Transactional(readOnly = false)
    public void update(long id, CombiAccountDto acc) throws AccountNotFoundException {
        CombiAccount account = combiAccountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        combiAccountRepository.save(account);
    }

    @Transactional(readOnly = false)
    public void delete(long id) throws AccountNotFoundException {
        if(!combiAccountRepository.existsById(id)){
            throw new AccountNotFoundException();
        }
        combiAccountRepository.deleteById(id);
    }

    private Account createSubAccount(Account account) {
        var accountNumber = AccountNumberGenerator.generate();
        var iban = new Iban.Builder()
                .countryCode(CountryCode.NL)
                .bankCode("INGB")
                .accountNumber(accountNumber)
                .build();
        account.setIban(iban.toFormattedString());
        account.setAccountNumber(accountNumber);
        return accountRepository.save(account);
    }
}
