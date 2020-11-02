package com.jim.security.controller;

import com.jim.security.exception.UserNotFoundException;
import com.jim.security.security.SecurityUtil;
import com.jim.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("Admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/user/{id}/addrole")
    public ResponseEntity addRole(@PathVariable("id") Long userId, @Valid @RequestBody Long roleId, BindingResult result) {
        if(SecurityUtil.isAuthenticatedUser(userId)) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        try {
            userService.addRole(userId, roleId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("user/{id}/removerole")
    public ResponseEntity removeRole(@PathVariable("id") Long userId, @Valid @RequestBody Long roleId, BindingResult result) {
        if(SecurityUtil.isAuthenticatedUser(userId)) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        if(result.hasErrors()) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        try {
            userService.removeRole(userId, roleId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}