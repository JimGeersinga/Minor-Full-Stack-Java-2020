package com.jim.spring_core.service;

import com.jim.spring_core.repository.StringRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseStringTransformService implements StringTransformService  {
    protected final StringRepository stringRepository;

    public Integer countWords(String value){
        final var cachedCount = stringRepository.getWordCount(value);
        if(cachedCount == null) {
            final var count = value.split(" ").length;
            stringRepository.saveWordCount(value, count);
            return count;
        }
        return cachedCount;
    }
}
