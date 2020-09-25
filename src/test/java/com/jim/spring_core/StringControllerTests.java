package com.jim.spring_core;


import com.jim.spring_core.service.StringServiceDev;
import com.jim.spring_core.service.StringServiceProd;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StringControllerTests {

    @Mock
    StringServiceDev stringServiceDev;

    @Mock
    StringServiceProd stringServiceProd;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void reverseTest() throws Exception {
        var text = "test";

        when(stringServiceDev.reverse(text)).thenReturn("tset");
        when(stringServiceProd.reverse(text)).thenReturn("TSET");

        mockMvc.perform(get("/reverse").param("value", text))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TSET")));

    }

    @Test
    void wordCountTest() throws Exception {
        var text = "this should be four";

        when(stringServiceDev.countWords(text)).thenReturn(4);
        when(stringServiceProd.countWords(text)).thenReturn(4);

        mockMvc.perform(get("/countWords").param("value", text))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("4")));

    }
}
