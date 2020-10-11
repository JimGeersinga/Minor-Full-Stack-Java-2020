package com.jim.spring_rest.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account extends BaseEntity {
    private String accountNumber;
    private Double amount;
    private Boolean isBlocked;

    @ManyToMany(mappedBy = "accounts")
    private Collection<AccountHolder> accountHolders = new ArrayList<>();

    public void addAccountHolder(AccountHolder accountHolder){
        this.accountHolders.add(accountHolder);
        accountHolder.getAccounts().add(this);
    }

    public void removeAccountHolder(AccountHolder accountHolder){
        this.accountHolders.remove(accountHolder);
        accountHolder.getAccounts().remove(this);
    }
}
