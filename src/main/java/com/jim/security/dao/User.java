package com.jim.security.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Getter
@Setter
@Entity
public class User extends BaseDao {
    private String username;

    private String password;

    @ManyToMany
    private Collection<User> friends;

    @Embedded
    private Address address;

    public void addFriend(User user){
        friends.add(user);
    }
    public void removeFriend(User user){
        friends.remove(user);
    }
}
