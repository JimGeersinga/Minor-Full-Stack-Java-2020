package com.jim.layers.controller;

import com.jim.layers.dao.CombiAccount;
import com.jim.layers.dto.CombiAccountDto;
import com.jim.layers.exception.AccountNotFoundException;
import com.jim.layers.mapper.CombiAccountMapper;
import com.jim.layers.service.CombiAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("CombiAccount")
@RequiredArgsConstructor
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

    @CachePut(value = "combiAccounts", key="#account.id")
    @PostMapping
    public ResponseEntity<CombiAccountDto> createCombiAccount(@Valid @RequestBody CombiAccountDto account, BindingResult result) {
        if(result.hasErrors()) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        CombiAccount createdAccount = combiAccountService.create(account);
        CombiAccountDto mappedAccount = combiAccountMapper.mapToDestination(createdAccount);
        return  new ResponseEntity<>(mappedAccount, HttpStatus.CREATED);
    }

    @CachePut(value = "combiAccounts", key="#account.id")
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

    @CacheEvict(value = "combiAccounts", key = "#id")
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
