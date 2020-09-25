package com.jim.spring_core;

import com.jim.spring_core.repository.StringRepository;
import com.jim.spring_core.service.StringServiceDev;
import com.jim.spring_core.service.StringServiceProd;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StringRepository.class, StringServiceDev.class, StringServiceProd.class})
class StringServiceTests {

    @Mock
    StringRepository stringRepository;

    @InjectMocks
    StringServiceDev stringServiceDev;

    @InjectMocks
    StringServiceProd stringServiceProd;

    @Test
    void constructorTestDev(){
        assertThat(stringServiceDev.GetStringRepository()).isEqualTo(stringRepository);
    }

    @Test
    void constructorTestProd(){
        assertThat(stringServiceProd.GetStringRepository()).isEqualTo(stringRepository);
    }

    @Test
    void testReverseDev() {
        // Given
        String text = "test";

        // Then
        assertThat(stringServiceDev.reverse(text)).isEqualTo("tset");
    }

    @Test
    void testReverseProd() {
        // Given
        String text = "test";

        // Then
        assertThat(stringServiceProd.reverse(text)).isEqualTo("TSET");
    }

    @Test
    void testWordCount() {
        // Given
        String text1 = "this will be four";
        String text2 = "this will be five definitely";

        // When
        when(stringRepository.getWordCount(text1)).thenReturn(null);
        when(stringRepository.getWordCount(text2)).thenReturn(5);

        // Then
        assertThat(stringServiceDev.countWords(text1)).isEqualTo(4);
        assertThat(stringServiceDev.countWords(text2)).isEqualTo(5);
        assertThat(stringServiceDev.countWords(text2)).isNotEqualTo(6);
    }
}
