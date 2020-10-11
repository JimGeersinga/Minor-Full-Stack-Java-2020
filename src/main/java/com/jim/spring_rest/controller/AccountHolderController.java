package com.jim.spring_rest.controller;

import com.jim.spring_rest.dao.Account;
import com.jim.spring_rest.dao.AccountHolder;
import com.jim.spring_rest.dto.AccountDto;
import com.jim.spring_rest.dto.AccountHolderDto;
import com.jim.spring_rest.service.AccountHolderService;
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
@RequestMapping("AccountHolder")
@RequiredArgsConstructor
public class AccountHolderController {

    private final AccountHolderService accountHolderService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<Collection<AccountHolderDto>> getAccountHolders(){
        Collection<AccountHolder> accountHolders = accountHolderService.getAll();
        List<AccountHolderDto> mappedAccountHolders = accountHolders.stream().map(x -> modelMapper.map(x, AccountHolderDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(mappedAccountHolders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountHolderDto> getAccountHolder(@PathVariable Long id) {
        Optional<AccountHolder> accountHolder = accountHolderService.getById(id);
        if(!accountHolder.isPresent()) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        AccountHolderDto mappedAccountHolder = modelMapper.map(accountHolder.get(), AccountHolderDto.class);
        return new ResponseEntity<>(mappedAccountHolder, HttpStatus.OK);
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<Collection<AccountDto>> getAccountsByAccountHolder(@PathVariable Long id) {
        Collection<Account> accounts = accountHolderService.getAccounts(id);
        List<AccountDto> mappedAccounts = accounts.stream().map(x -> modelMapper.map(x, AccountDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(mappedAccounts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountHolderDto> createAccountHolder(@Valid @RequestBody AccountHolderDto accountHolder, BindingResult result) {
        if(result.hasErrors()) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        AccountHolder createdAccount = accountHolderService.create(accountHolder);
        AccountHolderDto mappedAccountHolder = modelMapper.map(createdAccount, AccountHolderDto.class);
        return new ResponseEntity<>(mappedAccountHolder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAccountHolder(@PathVariable("id") Long id, @Valid @RequestBody AccountHolderDto accountHolder, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        try {
            accountHolderService.update(id, accountHolder);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccountHolder(@PathVariable Long id) {
        try {
            accountHolderService.delete(id);
            return  new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RelationNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
