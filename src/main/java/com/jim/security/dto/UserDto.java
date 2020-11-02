package com.jim.security.dto;

import com.jim.security.dao.Address;
import com.jim.security.dao.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class UserDto extends BaseDto {
    private String username;

    private Address address;

    private Collection<String> roles;
}
