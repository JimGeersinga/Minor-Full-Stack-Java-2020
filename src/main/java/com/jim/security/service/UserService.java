package com.jim.security.service;

import com.jim.security.dao.User;
import com.jim.security.dto.UserDto;
import com.jim.security.exception.UserNotFoundException;
import com.jim.security.mapper.UserMapper;
import com.jim.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Collection<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getById(long id){
        return userRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public User create(UserDto acc){
        User account = userMapper.mapToSource(acc);
        return userRepository.save(account);
    }

    @Transactional(readOnly = false)
    public void update(long id, UserDto acc) throws UserNotFoundException {
        User account = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.save(account);
    }

    @Transactional(readOnly = false)
    public void delete(long id) throws UserNotFoundException {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}