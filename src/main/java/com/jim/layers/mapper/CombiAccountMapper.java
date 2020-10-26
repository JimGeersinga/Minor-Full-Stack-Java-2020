package com.jim.layers.mapper;

import com.jim.layers.dao.CombiAccount;
import com.jim.layers.dto.CombiAccountDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CombiAccountMapper {
    CombiAccountDto mapToDestination(CombiAccount source);
    Collection<CombiAccountDto> mapToDestination(Collection<CombiAccount> source);

    CombiAccount mapToSource(CombiAccountDto destination);
    Collection<CombiAccount> mapToSource(Collection<CombiAccountDto> destination);
}
