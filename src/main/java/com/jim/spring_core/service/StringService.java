package com.jim.spring_core.service;

import com.jim.spring_core.repository.StringRepository;

public interface StringService {

    StringRepository GetStringRepository();

    String reverse(String value);

    default int countWords(String value){
        var repository = GetStringRepository();

        var wordCount = repository.getWordCount(value);
        if(wordCount == null) {
            wordCount = value.split(" ").length;
            repository.saveWordCount(value, wordCount);
        }
        return  wordCount;
    }
}
