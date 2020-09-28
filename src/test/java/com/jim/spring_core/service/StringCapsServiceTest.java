package com.jim.spring_core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"caps"})
@ExtendWith(MockitoExtension.class)
class StringCapsServiceTest {

    @InjectMocks
    StringCapsService stringCapsService;

    @Test
    void capsTest() {
        // Given
        String input = "test";

        // Then
        assertThat(stringCapsService.transform(input)).isEqualTo("TEST");
    }
}
