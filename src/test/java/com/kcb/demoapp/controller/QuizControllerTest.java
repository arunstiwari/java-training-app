package com.kcb.demoapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_CreateQuizQuestion() throws Exception {
        String questionJson = "{\"questionId\":\"QUES-1\",\"questionText\":[\"Dummy Question\"],\"options\":[{\"choiceText\":\"t\",\"isCorrect\":1},{\"choiceText\":\"c\",\"isCorrect\":0},{\"choiceText\":\"b\",\"isCorrect\":0},{\"choiceText\":\"a\",\"isCorrect\":0}],\"correctAnswer\":\"t\",\"category\":\"Dummy\",\"tags\":[\"dummy\",\"vedantu\"],\"explanation\":[\"dummy question\"],\"references\":[\"http://www.example.com\"],\"questionImage\":\"\",\"questionVideo\":\"\",\"questionAudio\":\"\",\"questionType\":\"multiple_choice\",\"complexity\":\"EASY\"}";
        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetAllQuestions() throws Exception {
        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldSearchQuestionsByCategoryAndComplexity() throws Exception {
        mockMvc.perform(get("/questions/search")
                        .param("category", "Dummy")
                        .param("complexity", "EASY"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnBadRequestForInvalidComplexity() throws Exception {
        mockMvc.perform(get("/questions/search")
                        .param("complexity", "invalid"))
                .andExpect(status().isBadRequest());
    }

}
