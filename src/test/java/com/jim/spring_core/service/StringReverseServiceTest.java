package com.jim.spring_core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StringReverseServiceTest {

    @InjectMocks
    StringReverseService stringReverseService;

    @Test
    void reverseTest() {
        assertThat(stringReverseService.transform("test")).isEqualTo("tset");
        assertThat(stringReverseService.transform(null)).isEqualTo("");
    }
}
