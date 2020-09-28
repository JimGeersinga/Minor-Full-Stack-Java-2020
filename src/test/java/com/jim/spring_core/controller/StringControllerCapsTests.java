package com.jim.spring_core.controller;

import com.jim.spring_core.service.StringTransformService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"prod"})
@WebMvcTest(StringController.class)
class StringControllerCapsTests {

    @MockBean
    StringTransformService stringTransformService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void capsTest() throws Exception {
        // Given
        var input = "test";

        // When
        when(stringTransformService.transform(input)).thenReturn("TEST");

        // Then
        mockMvc.perform(get("/string/transform").param("value", input))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TEST")));
    }
}
