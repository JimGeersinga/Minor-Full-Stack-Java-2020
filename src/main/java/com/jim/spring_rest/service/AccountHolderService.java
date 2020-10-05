package com.jim.spring_rest.service;

import com.jim.spring_rest.model.Account;
import com.jim.spring_rest.model.AccountHolder;
import com.jim.spring_rest.repository.AccountHolderRepository;
import com.jim.spring_rest.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationNotFoundException;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AccountHolderService {
    private final AccountRepository accountRepository;
    private final AccountHolderRepository accountHolderRepository;

    public Collection<AccountHolder> getAll(){
        return accountHolderRepository.getAll();
    }

    public AccountHolder getById(int id){
        return accountHolderRepository.getById(id);
    }
    public Collection<Account> getAccounts(int accountHolderId) {
        return accountRepository.getByAccountHolderId(accountHolderId);
    }

    public AccountHolder create(AccountHolder accountHolder){
        return accountHolderRepository.create(accountHolder);
    }

    public void update(int id, AccountHolder accountHolder) throws RelationNotFoundException {
        if(!accountHolderRepository.exists(id)) {
            throw new RelationNotFoundException("accountholder not found");
        }
        accountHolderRepository.update(id, accountHolder);
    }

    public void delete(int id) throws RelationNotFoundException {
        if(!accountHolderRepository.exists(id)) {
            throw new RelationNotFoundException("accountholder not found");
        }
        accountHolderRepository.delete(id);
    }
}