package com.jim.spring_rest.service;

import com.jim.spring_rest.dao.Account;
import com.jim.spring_rest.dao.AccountHolder;
import com.jim.spring_rest.dto.AccountDto;
import com.jim.spring_rest.repository.AccountHolderRepository;
import com.jim.spring_rest.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RelationNotFoundException;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountHolderRepository accountHolderRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Collection<Account> getAll(){
        return accountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Account> getById(long id){
        return accountRepository.findById(id);
    }

    @Transactional
    public void block(long id, boolean isBlocked) throws RelationNotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        if(!account.isPresent()){
            throw new RelationNotFoundException("account not found");
        }
        Account acc = account.get();
        acc.setIsBlocked(isBlocked);
        accountRepository.save(acc);
    }

    public Account create(AccountDto account){
        Account acc = modelMapper.map(account, Account.class);
        return accountRepository.save(acc);
    }

    @Transactional
    public void update(long id, AccountDto account) throws RelationNotFoundException {
        Optional<Account> accountFromDb = accountRepository.findById(id);
        if(!accountFromDb.isPresent()) {
            throw new RelationNotFoundException("account not found");
        }
        Account acc = accountFromDb.get();
        acc.setAccountNumber(account.getAccountNumber());
        acc.setIsBlocked(account.getIsBlocked());
        acc.setAmount(account.getAmount());
        accountRepository.save(acc);
    }

    @Transactional
    public void delete(long id) throws RelationNotFoundException {
        if(!accountRepository.existsById(id)){
            throw new RelationNotFoundException("account not found");
        }
        accountRepository.deleteById(id);
    }

    @Transactional
    public void link(long accountId, long accountHolderId, boolean link) throws RelationNotFoundException {
        Optional<Account> account = accountRepository.findById(accountId);
        if(!account.isPresent()){
            throw new RelationNotFoundException("account not found");
        }
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(accountHolderId);
        if(!accountHolder.isPresent()){
            throw new RelationNotFoundException("accountholder not found");
        }

        Account acc  = account.get();
        if(link){
            acc.addAccountHolder(accountHolder.get());
        }else{
            acc.removeAccountHolder(accountHolder.get());
        }
        accountRepository.save(acc);
    }
}
