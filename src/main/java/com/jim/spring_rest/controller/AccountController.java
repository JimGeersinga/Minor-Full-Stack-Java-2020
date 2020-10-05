package com.jim.spring_rest.controller;

import com.jim.spring_rest.model.Account;
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

@RequestMapping("Account")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountHolderService accountHolderService;

    @GetMapping()
    public ResponseEntity<Collection<Account>> getAccounts(){
        var accounts = accountService.getAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Integer id) {
        var account = accountService.getById(id);
        if(account == null) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return  new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account, BindingResult result) {
        if(result.hasErrors()) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var createdAccount = accountService.create(account);
        return  new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity blockAccount(@PathVariable Integer id) {
        if(accountService.getById(id) == null) return  new ResponseEntity("account not found",HttpStatus.NOT_FOUND);

        try {
            accountService.block(id, true);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/unblock")
    public ResponseEntity unblockAccount(@PathVariable Integer id) {
        if(accountService.getById(id) == null) return  new ResponseEntity("account not found",HttpStatus.NOT_FOUND);

        try {
            accountService.block(id, false);
        } catch (RelationNotFoundException e) {
            return  new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAccount(@PathVariable("id") Integer id, @Valid @RequestBody Account account, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        var savedAccount = accountService.getById(id);
        if(savedAccount == null) return  new ResponseEntity("account not found",HttpStatus.NOT_FOUND);

        try {
            accountService.update(id, account);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable Integer id) {
        if(accountService.getById(id) == null) return new ResponseEntity("account not found",HttpStatus.NOT_FOUND);
        try {
            accountService.delete(id);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{accountId}/accountHolder/{accountHolderId}")
    public ResponseEntity linkAccount(@PathVariable Integer accountId, @PathVariable Integer accountHolderId){
        if(accountService.getById(accountId) == null){
            return new ResponseEntity("account not found",HttpStatus.NOT_FOUND);
        }
        if(accountHolderService.getById(accountHolderId) == null){
            return new ResponseEntity("accountHolder not found",HttpStatus.NOT_FOUND);
        }

        try {
            accountService.link(accountId, accountHolderId);
        } catch (RelationNotFoundException e) {
            return  new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{accountId}/accountHolder/{accountHolderId}")
    public ResponseEntity unlinkAccount(@PathVariable Integer accountId, @PathVariable Integer accountHolderId){
        if(accountService.getById(accountId) == null){
            return new ResponseEntity("account not found",HttpStatus.NOT_FOUND);
        }
        if(accountHolderService.getById(accountHolderId) == null){
            return new ResponseEntity("accountHolder not found",HttpStatus.NOT_FOUND);
        }

        try {
            accountService.unlink(accountId, accountHolderId);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
