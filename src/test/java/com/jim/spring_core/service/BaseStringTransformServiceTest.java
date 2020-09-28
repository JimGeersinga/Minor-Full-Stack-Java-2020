package com.jim.spring_core.service;

import com.jim.spring_core.repository.StringRepository;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseStringTransformServiceTest {

    static  class  BaseStringTransformServiceTestImpl extends BaseStringTransformService {
        public BaseStringTransformServiceTestImpl(StringRepository stringRepository) {super(stringRepository);}

        @Override
        public String transform(String input) {
            throw new UnsupportedOperationException("Not important for this test");
        }
    }

    @Mock
    StringRepository stringRepository;

    @InjectMocks
    BaseStringTransformServiceTestImpl stringTransformService;

    @Test
    void testCachedWordCount() {
        when(stringRepository.getWordCount("this will be four")).thenReturn(4);

        assertThat(stringTransformService.countWords("this will be four")).isEqualTo(4);

        verify(stringRepository, times(1)).getWordCount("this will be four");
    }

    @Test
    void testWordCount() {
        when(stringRepository.getWordCount("this will be four")).thenReturn(null);

        assertThat(stringTransformService.countWords("this will be four")).isEqualTo(4);

        verify(stringRepository, times(1)).getWordCount("this will be four");
        verify(stringRepository, times(1)).saveWordCount("this will be four", 4);
    }
}
