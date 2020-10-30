package com.jim.security.mapper;

import com.jim.security.dao.User;
import com.jim.security.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapToDestination(User source);
    Collection<UserDto> mapToDestination(Collection<User> source);

    User mapToSource(UserDto destination);
    Collection<User> mapToSource(Collection<UserDto> destination);
}