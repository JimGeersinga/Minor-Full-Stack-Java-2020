package com.jim.spring_rest.controller;

import com.jim.spring_rest.dao.Account;
import com.jim.spring_rest.dto.AccountDto;
import com.jim.spring_rest.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationNotFoundException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("Account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<Collection<AccountDto>> getAccounts(){
        Collection<Account> accounts = accountService.getAll();
        List<AccountDto> mappedAccounts = accounts.stream().map(x -> modelMapper.map(x, AccountDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(mappedAccounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        Optional<Account> account = accountService.getById(id);
        if(!account.isPresent()) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        AccountDto mappedAccount = modelMapper.map(account.get(), AccountDto.class);
        return  new ResponseEntity<>(mappedAccount, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto account, BindingResult result) {
        if(result.hasErrors()) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Account createdAccount = accountService.create(account);
        AccountDto mappedAccount = modelMapper.map(createdAccount, AccountDto.class);
        return  new ResponseEntity<>(mappedAccount, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity blockAccount(@PathVariable Long id) {
        try {
            accountService.block(id, true);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/unblock")
    public ResponseEntity unblockAccount(@PathVariable Long id) {
        try {
            accountService.block(id, false);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RelationNotFoundException e) {
            return  new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAccount(@PathVariable("id") Long id, @Valid @RequestBody AccountDto account, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        try {
            accountService.update(id, account);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        try {
            accountService.delete(id);
            return  new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{accountId}/accountHolder/{accountHolderId}")
    public ResponseEntity linkAccount(@PathVariable Long accountId, @PathVariable Long accountHolderId){
        try {
            accountService.link(accountId, accountHolderId, true);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (RelationNotFoundException e) {
            return  new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{accountId}/accountHolder/{accountHolderId}")
    public ResponseEntity unlinkAccount(@PathVariable Long accountId, @PathVariable Long accountHolderId){
        try {
            accountService.link(accountId, accountHolderId, false);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
