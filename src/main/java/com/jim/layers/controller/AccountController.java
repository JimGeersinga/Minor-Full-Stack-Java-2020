package com.jim.layers.controller;

import com.jim.layers.dao.Account;
import com.jim.layers.dto.AccountDto;
import com.jim.layers.exception.AccountNotFoundException;
import com.jim.layers.mappers.AccountMapper;
import com.jim.layers.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("Account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping()
    public ResponseEntity<Collection<AccountDto>> getAccounts(){
        Collection<Account> accounts = accountService.getAll();
        Collection<AccountDto> mappedAccounts = accountMapper.mapToDestination(accounts);
        return new ResponseEntity<>(mappedAccounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        Optional<Account> account = accountService.getById(id);
        if(!account.isPresent()) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        AccountDto mappedAccount = accountMapper.mapToDestination(account.get());
        return  new ResponseEntity<>(mappedAccount, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto account, BindingResult result) {
        if(result.hasErrors()) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Account createdAccount = accountService.create(account);
        AccountDto mappedAccount = accountMapper.mapToDestination(createdAccount);
        return  new ResponseEntity<>(mappedAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAccount(@PathVariable("id") Long id, @Valid @RequestBody AccountDto account, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        try {
            accountService.update(id, account);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        try {
            accountService.delete(id);
            return  new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
