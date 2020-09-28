package com.jim.spring_core.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StringRepository {

    private final Map<String, Integer> _textWordCount;

    public Integer getWordCount(String text) {
        return _textWordCount.getOrDefault(text, null);
    }

    public void saveWordCount(String text, Integer count) {
        _textWordCount.put(text, count);
    }
}
