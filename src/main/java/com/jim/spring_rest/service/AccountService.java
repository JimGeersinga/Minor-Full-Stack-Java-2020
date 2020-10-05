package com.jim.spring_rest.service;

import com.jim.spring_rest.model.Account;
import com.jim.spring_rest.repository.AccountHolderRepository;
import com.jim.spring_rest.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationNotFoundException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountHolderRepository accountHolderRepository;

    public ArrayList<Account> getAll(){
        return accountRepository.getAll();
    }

    public Account getById(int id){
        return accountRepository.getById(id);
    }

    public void block(int id, boolean block) throws RelationNotFoundException {
        if(!accountRepository.exists(id)){
            throw new RelationNotFoundException("account not found");
        }
        accountRepository.block(id, block);
    }

    public Account create(Account account){
        return accountRepository.create(account);
    }

    public void update(int id, Account account) throws RelationNotFoundException {
        if(!accountRepository.exists(id)){
            throw new RelationNotFoundException("account not found");
        }
        accountRepository.update(id, account);
    }
    public void delete(int id) throws RelationNotFoundException {
        if(!accountRepository.exists(id)){
            throw new RelationNotFoundException("account not found");
        }
        accountRepository.delete(id);
    }

    public void link(int accountId, int accountHolderId) throws RelationNotFoundException {
        if(!accountRepository.exists(accountId)){
            throw new RelationNotFoundException("account not found");
        }
        if(!accountHolderRepository.exists(accountHolderId)){
            throw new RelationNotFoundException("accountholder not found");
        }
        accountRepository.link(accountId, accountHolderId);
    }
    public void unlink(int accountId, int accountHolderId) throws RelationNotFoundException {
        if(!accountRepository.exists(accountId)){
            throw new RelationNotFoundException("account not found");
        }
        if(!accountHolderRepository.exists(accountHolderId)){
            throw new RelationNotFoundException("accountholder not found");
        }
        accountRepository.unlink(accountId, accountHolderId);
    }
}
