package com.jim.spring_core.service;

import com.jim.spring_core.repository.StringRepository;

public interface StringService {

    StringRepository GetStringRepository();

    String reverse(String value);

    default Integer countWords(String value){
        var repository = GetStringRepository();

        var wordCount = repository.getWordCount(value);
        if(wordCount == null) {
            wordCount = value.split(" ").length;
            repository.saveWordCount(value, wordCount);
            return  repository.getWordCount(value);
        }
        return wordCount;
    }
}
