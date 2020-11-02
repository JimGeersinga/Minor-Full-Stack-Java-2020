package com.jim.security.controller;

import com.jim.security.dao.User;
import com.jim.security.dto.UserDto;
import com.jim.security.exception.UserNotFoundException;
import com.jim.security.mapper.UserMapper;
import com.jim.security.security.SecurityUtil;
import com.jim.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    public ResponseEntity<Collection<UserDto>> getUsers(){
        Collection<User> users = userService.getAll();
        Collection<UserDto> mappedUsers = users.parallelStream().map(userMapper::mapToDestination).collect(Collectors.toList());
        return new ResponseEntity<>(mappedUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getById(id);
        if(!user.isPresent()) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        UserDto mappedUser = userMapper.mapToDestination(user.get());
        return  new ResponseEntity<>(mappedUser, HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<Collection<UserDto>> getFriends(@PathVariable Long id) {
        Optional<User> user = userService.getById(id);
        if(!user.isPresent()) return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Collection<UserDto> mappedUsers = user.get().getFriends().parallelStream().map(userMapper::mapToDestination).collect(Collectors.toList());
        return  new ResponseEntity<>(mappedUsers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long userId, @Valid @RequestBody UserDto user, BindingResult result) {
        if(!SecurityUtil.isAuthenticatedUser(userId) || !SecurityUtil.hasRole("Admin")) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        try {
            userService.update(userId, user);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return  new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

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

    @PostMapping("/{id}/removefriend")
    public ResponseEntity removeFriend(@PathVariable("id") Long userId, @Valid @RequestBody Long friendId, BindingResult result) {
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        try {
            userService.removeFriend(userId, friendId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}