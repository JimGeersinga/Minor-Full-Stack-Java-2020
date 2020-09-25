package com.jim.spring_core.service;

import com.jim.spring_core.repository.StringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class StringServiceProd implements StringService {

    @Autowired
    private StringRepository _stringRepository;

    public StringRepository GetStringRepository(){
        return  _stringRepository;
    }

    public String reverse(String value){
        return new StringBuilder(value).reverse().toString().toUpperCase();
    }
}
