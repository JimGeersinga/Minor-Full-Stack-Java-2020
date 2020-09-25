package com.jim.spring_core;

import com.jim.spring_core.repository.StringRepository;
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
        // Given
        var text = "this will be four";

        // When
        _stringRepository.saveWordCount(text, 4);

        // Then
        assertThat(_stringRepository.getWordCount(text)).isEqualTo(4);
    }

    @Test
    void wordCountGetTest(){
        // Given
        var text1 = "this will be four";
        var text2 = "this will be five indeed";
         _stringRepository.saveWordCount(text2, 5);

        // When
        var result1 = _stringRepository.getWordCount(text1);
        var result2 = _stringRepository.getWordCount(text2);

        // Then
        assertThat(result1).isEqualTo(null);
        assertThat(result2).isEqualTo(5);
    }

}
