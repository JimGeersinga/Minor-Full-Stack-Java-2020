package com.jim.security.controller;

import com.jim.security.dao.User;
import com.jim.security.dto.UserDto;
import com.jim.security.exception.UserNotFoundException;
import com.jim.security.mapper.UserMapper;
import com.jim.security.service.UserService;
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

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Cacheable("users")
    @GetMapping()
    public ResponseEntity<Collection<UserDto>> getAccounts(){
        Collection<User> users = userService.getAll();
        Collection<UserDto> mappedUsers = userMapper.mapToDestination(users);
        return new ResponseEntity<>(mappedUsers, HttpStatus.OK);
    }

    @Cacheable("users")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getById(id);
        if(!user.isPresent()) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserDto mappedUser = userMapper.mapToDestination(user.get());
        return  new ResponseEntity<>(mappedUser, HttpStatus.OK);
    }

    @CachePut(value = "users", key="#user.id")
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto user, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        try {
            userService.update(id, user);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @CacheEvict(value = "users", key = "#id")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return  new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Caching(put = {
        @CachePut(value = "users", key = "#userId"),
        @CachePut(value = "users", key = "#friendId")
    })
    @PostMapping("/{id}/addfriend")
    public ResponseEntity addFriend(@PathVariable("id") Long userId, @Valid @RequestBody Long friendId, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        try {
            userService.addFriend(userId, friendId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}