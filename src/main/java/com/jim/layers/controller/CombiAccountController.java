package com.jim.layers.controller;

import com.jim.layers.PerformanceLogging;
import com.jim.layers.dao.CombiAccount;
import com.jim.layers.dto.CombiAccountDto;
import com.jim.layers.exception.AccountNotFoundException;
import com.jim.layers.mapper.CombiAccountMapper;
import com.jim.layers.service.CombiAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@PerformanceLogging
@RequestMapping("CombiAccount")
@RequiredArgsConstructor
@RestController
public class CombiAccountController {

    private final CombiAccountService combiAccountService;
    private final CombiAccountMapper combiAccountMapper;

    @Cacheable("combiAccounts")
    @GetMapping()
    public ResponseEntity<Collection<CombiAccountDto>> getCombiAccounts(){
        Collection<CombiAccount> accounts = combiAccountService.getAll();
        Collection<CombiAccountDto> mappedAccounts = combiAccountMapper.mapToDestination(accounts);
        return new ResponseEntity<>(mappedAccounts, HttpStatus.OK);
    }

    @Cacheable("combiAccounts")
    @GetMapping("/{id}")
    public ResponseEntity<CombiAccountDto> getCombiAccount(@PathVariable Long id) {
        Optional<CombiAccount> account = combiAccountService.getById(id);
        if(!account.isPresent()) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        CombiAccountDto mappedAccount = combiAccountMapper.mapToDestination(account.get());
        return  new ResponseEntity<>(mappedAccount, HttpStatus.OK);
    }

    @Caching(evict = {
        @CacheEvict(value = "combiAccounts", allEntries = true),
        @CacheEvict(value = "accounts", allEntries = true)
    })
    @PostMapping
    public ResponseEntity<CombiAccountDto> createCombiAccount(@Valid @RequestBody CombiAccountDto account, BindingResult result) {
        if(result.hasErrors()) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        CombiAccount createdAccount = combiAccountService.create(account);
        CombiAccountDto mappedAccount = combiAccountMapper.mapToDestination(createdAccount);
        return  new ResponseEntity<>(mappedAccount, HttpStatus.CREATED);
    }

    @Caching(put = {
        @CachePut(value = "combiAccounts", key = "#account.id"),
        @CachePut(value = "accounts", key = "#account.accountA.id"),
        @CachePut(value = "accounts", key = "#account.accountB.id"),
    })
    @PutMapping("/{id}")
    public ResponseEntity updateCombiAccount(@PathVariable("id") Long id, @Valid @RequestBody CombiAccountDto account, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        try {
            combiAccountService.update(id, account);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Caching(evict = {
        @CacheEvict(value = "combiAccounts", key = "#id"),
        @CacheEvict(value = "accounts", allEntries = true)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCombiAccount(@PathVariable Long id) {
        try {
            combiAccountService.delete(id);
            return  new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
