package com.jim.spring_rest.repository;

import com.jim.spring_rest.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final Map<Integer, Account> accounts;
    private final Map<Integer, HashSet<Integer>> accountLinks;

    public boolean exists(int id){
        return  accounts.containsKey(id);
    }

    public ArrayList<Account> getAll(){
        return new ArrayList(accounts.values());
    }

    public Account getById(int id){
        return accounts.getOrDefault(id, null);
    }

    public Collection<Account> getByAccountHolderId(int accountHolderId) {
        var accountIds = accountLinks.entrySet().stream()
                .filter(x -> x.getValue().contains(accountHolderId))
                .map(x -> x.getKey());
        return accounts.entrySet().stream()
                .filter(x -> accountIds.anyMatch(aId -> aId.equals(x.getKey())))
                .map(x -> x.getValue())
                .collect(Collectors.toList());
    }

    public Account create(Account account) {
        int id = getNextAccountId();
        account.setId(id);
        accounts.put(id, account);
        return account;
    }

    public void block(int id, boolean block){
        var account = accounts.get(id);
        account.setIsBlocked(block);
    }

    public void update(int id, Account account){
        accounts.put(id, account);
    }

    public void delete(int id){
        accounts.remove(id);
    }

    public void link(int accountId, int accountHolderId) {
        HashSet accountHolders = new HashSet<Integer>();
        if (accountLinks.containsKey(accountId)) {
            accountHolders = accountLinks.get(accountId);
        }
        accountHolders.add(accountHolderId);
        accountLinks.put(accountId, accountHolders);
    }

    public void unlink(int accountId, int accountHolderId) {
        if (accountLinks.containsKey(accountId)) {
            var accountHolders = accountLinks.get(accountId);
            accountHolders.remove(accountHolderId);
            accountLinks.put(accountId, accountHolders);
        }
    }

    private Integer getNextAccountId() {
        return (accounts.keySet().stream().max(Comparator.naturalOrder()).orElse(0) + 1);
    }


}
