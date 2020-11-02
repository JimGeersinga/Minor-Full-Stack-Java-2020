package com.jim.security.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseDao {
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String username;

    @NotEmpty
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @ManyToMany
    private Set<User> friends;

    @Embedded
    private Address address;

    public void addFriend(User user){
        friends.add(user);
    }
    public void removeFriend(User user){
        friends.remove(user);
    }

    public void addRole(Role role){
        roles.add(role);
    }
    public void removeRole(Role role){
        roles.remove(role);
    }
}
