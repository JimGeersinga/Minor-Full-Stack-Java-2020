package com.jim.spring_core.service;

import com.jim.spring_core.repository.StringRepository;
import org.springframework.stereotype.Service;

@Service
public class StringReverseService extends BaseStringTransformService {

    public StringReverseService(StringRepository stringRepository) {
        super(stringRepository);
    }

    @Override
    public String transform(String input){
        return new StringBuilder(input != null ? input : "").reverse().toString();
    }
}
