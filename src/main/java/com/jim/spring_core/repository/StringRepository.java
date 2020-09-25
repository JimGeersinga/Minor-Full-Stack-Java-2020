package com.jim.spring_core.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StringRepository {

    private final Map<String, Integer> _textWordCount;

    public StringRepository(){
        _textWordCount = new HashMap<>();
    }

    public Integer getWordCount(String text) {
        if(!_textWordCount.containsKey(text)){
            return null;
        }
        return  _textWordCount.get(text);
    }

    public void saveWordCount(String text, Integer count) {
        if(!_textWordCount.containsKey(text)){
            _textWordCount.put(text, count);
        }
    }
}
