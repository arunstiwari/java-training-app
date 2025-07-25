package com.kcb.demoapp.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TrustedHostMiddlewareTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAllowedHost() throws Exception {
        mockMvc.perform(get("/questions")
                .header("Host", "localhost"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/questions")
                        .header("Host", "tekmentors.com"))
                .andExpect(status().isOk());
    }

    @Test
    void testBlockedHost() throws Exception {
        mockMvc.perform(get("/questions")
                        .header("Host", "malicious.com"))
                .andExpect(status().isForbidden());

    }
}