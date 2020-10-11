package com.jim.spring_rest;

import com.jim.spring_rest.dao.AccountHolder;
import com.jim.spring_rest.dto.AccountHolderDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(AccountHolder.class, AccountHolderDto.class).addMapping(AccountHolder::getAddress, AccountHolderDto::setAddress);
        modelMapper.typeMap(AccountHolderDto.class, AccountHolder.class).addMapping(AccountHolderDto::getAddress, AccountHolder::setAddress);
        return modelMapper;
    }
}
