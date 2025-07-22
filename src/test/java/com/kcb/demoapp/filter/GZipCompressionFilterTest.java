package com.kcb.demoapp.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GZipCompressionFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGZipCompressionEnabled() throws Exception {
        mockMvc.perform(get("/questions")
                .header("Accept-Encoding", "gzip"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Encoding", "gzip"));

    }

    @Test
    void testGZipCompressionDisabled() throws Exception {
        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(header().doesNotExist("Content-Encoding"));

    }
}