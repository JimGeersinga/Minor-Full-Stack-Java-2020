package com.jim.spring_rest.repository;

import com.jim.spring_rest.model.AccountHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AccountHolderRepository {

    private final Map<Integer, AccountHolder> accountHolders;

    public boolean exists(int id){
        return accountHolders.containsKey(id);
    }

    public Collection<AccountHolder> getAll(){
        return accountHolders.values();
    }
    public AccountHolder getById(int id){
        return accountHolders.getOrDefault(id, null);
    }

    public AccountHolder create(AccountHolder accountHolder) {
        int id = getNextAccountHolderId();
        accountHolder.setId(id);
        accountHolders.put(id, accountHolder);
        return accountHolder;
    }

    public void update(int id, AccountHolder accountHolder){
        accountHolders.put(id, accountHolder);
    }

    public void delete(int id){
            accountHolders.remove(id);
    }

    private Integer getNextAccountHolderId() {
        return (accountHolders.keySet().stream().max(Comparator.naturalOrder()).orElse(0) + 1);
    }
}
