package com.jim.spring_core.service;

import com.jim.spring_core.repository.StringRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile({"caps"})
public class StringCapsService extends BaseStringTransformService {

    public StringCapsService(StringRepository stringRepository) {
        super(stringRepository);
    }

    @Override
    public String transform(String input){
        return input.toUpperCase();
    }
}
