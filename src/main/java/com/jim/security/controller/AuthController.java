package com.jim.security.controller;

import com.jim.security.dao.User;
import com.jim.security.dto.AuthDto;
import com.jim.security.dto.UserDto;
import com.jim.security.mapper.UserMapper;
import com.jim.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody AuthDto user, BindingResult result) {
        if(result.hasErrors()) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User createdUser = userService.create(newUser);
        UserDto mappedUser = userMapper.mapToDestination(createdUser);
        return  new ResponseEntity<>(mappedUser, HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody Object data) {
//        try {
////            String username = data.getUsername();
////            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
////
////            return ok(model);
//            return null;
//        } catch (AuthenticationException e) {
//            return new ResponseEntity<>("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
//        }
//    }
}
