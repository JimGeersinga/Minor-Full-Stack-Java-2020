package com.jim.spring_core.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StringRepository.class})
class StringRepositoryTests {

    @Autowired
    private StringRepository _stringRepository;

    @Test
    void wordCountSaveTest(){
        // When
        _stringRepository.saveWordCount("this will be four", 4);

        // Then
        assertThat(_stringRepository.getWordCount("this will be four")).isEqualTo(4);
    }

    @Test
    void wordCountGetTest(){
        // Given
         _stringRepository.saveWordCount("this will be five indeed", 5);

        // When
        var result1 = _stringRepository.getWordCount("this will be four");
        var result2 = _stringRepository.getWordCount("this will be five indeed");

        // Then
        assertThat(result1).isEqualTo(null);
        assertThat(result2).isEqualTo(5);
    }

}
