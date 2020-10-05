package com.jim.spring_rest.controller;

import com.jim.spring_rest.model.Account;
import com.jim.spring_rest.model.AccountHolder;
import com.jim.spring_rest.service.AccountHolderService;
import com.jim.spring_rest.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationNotFoundException;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("AccountHolder")
@RequiredArgsConstructor
public class AccountHolderController {

    private final AccountHolderService accountHolderService;
    private final AccountService accountService;

    public ResponseEntity<Collection<Account>> getAccountHolders(){
        var accounts = accountService.getAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountHolder> getAccountHolder(@PathVariable Integer id) {
        var account = accountHolderService.getById(id);
        if(account == null) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return  new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<Collection<Account>> getAccountsByAccountHolder(@PathVariable Integer id) {
        if(accountHolderService.getById(id) == null) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var accounts = accountHolderService.getAccounts(id);

        return  new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountHolder> createAccountHolder(@Valid @RequestBody AccountHolder accountHolder, BindingResult result) {
        if(result.hasErrors()) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var createdAccount = accountHolderService.create(accountHolder);
        return  new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAccountHolder(@PathVariable("id") Integer id, @Valid @RequestBody AccountHolder accountHolder, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        var savedAccountHolder = accountHolderService.getById(id);
        if(savedAccountHolder == null) return  new ResponseEntity("accountHolder not found",HttpStatus.NOT_FOUND);

        try {
            accountHolderService.update(id, accountHolder);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccountHolder(@PathVariable Integer id) {
        if(accountHolderService.getById(id) == null) return new ResponseEntity("accountHolder not found",HttpStatus.NOT_FOUND);
        try {
            accountHolderService.delete(id);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
