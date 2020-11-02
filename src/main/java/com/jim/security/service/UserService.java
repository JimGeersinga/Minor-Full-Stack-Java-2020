package com.jim.security.service;

import com.jim.security.dao.Role;
import com.jim.security.dao.User;
import com.jim.security.dao.UserPrincipal;
import com.jim.security.dto.UserDto;
import com.jim.security.exception.UserNotFoundException;
import com.jim.security.repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    public Collection<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getById(long id){
        return userRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public User create(User user){
        return userRepository.save(user);
    }

    @Transactional(readOnly = false)
    public void update(long id, UserDto u) throws UserNotFoundException {
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

    @Transactional(readOnly = false)
    public void addFriend(long userId, long friendId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        User friend = userRepository.findById(friendId).orElseThrow(UserNotFoundException::new);

        user.addFriend(friend);
        userRepository.save(user);

        friend.addFriend(user);
        userRepository.save(friend);
    }

    @Transactional(readOnly = false)
    public void removeFriend(long userId, long friendId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        User friend = userRepository.findById(friendId).orElseThrow(UserNotFoundException::new);

        user.removeFriend(friend);
        userRepository.save(user);

        friend.removeFriend(user);
        userRepository.save(friend);
    }

    @Transactional(readOnly = false)
    public void addRole(long userId, long roleId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Role role = roleRepository.findById(roleId).orElseThrow(UserNotFoundException::new);

        user.addRole(role);
        userRepository.save(user);
    }

    @Transactional(readOnly = false)
    public void removeRole(long userId, long roleId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Role role = roleRepository.findById(roleId).orElseThrow(UserNotFoundException::new);

        user.removeRole(role);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        return new UserPrincipal(user);
    }
}