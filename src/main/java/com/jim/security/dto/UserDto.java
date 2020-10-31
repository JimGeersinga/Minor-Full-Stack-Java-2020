package com.jim.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class UserDto extends BaseDto {
    private String username;

    private Collection<UserDto> friends;
}
