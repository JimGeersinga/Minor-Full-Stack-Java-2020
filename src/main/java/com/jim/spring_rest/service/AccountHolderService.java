package com.jim.spring_rest.service;

import com.jim.spring_rest.dao.Account;
import com.jim.spring_rest.dao.AccountHolder;
import com.jim.spring_rest.dto.AccountHolderDto;
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
public class AccountHolderService {
    private final AccountRepository accountRepository;
    private final AccountHolderRepository accountHolderRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Collection<AccountHolder> getAll(){
        return accountHolderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<AccountHolder> getById(long id){
        return accountHolderRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Collection<Account> getAccounts(long accountHolderId) {
        return accountRepository.getByAccountHolderId(accountHolderId);
    }

    public AccountHolder create(AccountHolderDto accountHolder){
        AccountHolder accHolder = modelMapper.map(accountHolder, AccountHolder.class);
        return accountHolderRepository.save(accHolder);
    }

    @Transactional
    public void update(long id, AccountHolderDto accountHolder) throws RelationNotFoundException {
        Optional<AccountHolder> accountHolderFromDb = accountHolderRepository.findById(id);
        AccountHolder accHolder = accountHolderFromDb.orElseThrow(() -> new RelationNotFoundException("accountholder not found"));

        accHolder.setName(accountHolder.getName());

        accountHolderRepository.save(accHolder);
    }

    @Transactional
    public void delete(long id) throws RelationNotFoundException {
        if(!accountHolderRepository.existsById(id)) {
            throw new RelationNotFoundException("accountholder not found");
        }
        accountHolderRepository.deleteById(id);
    }
}