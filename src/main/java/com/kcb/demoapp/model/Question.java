package com.kcb.demoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@NoArgsConstructor
@Data
public class Question {
    @Id
    private String questionId;
    @ElementCollection
    private List<String> questionText;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChoiceOption> options;
    private String correctAnswer;
    private String category;
    @ElementCollection
    private List<String> tags;
    @ElementCollection
    private List<String> explanation;
    @ElementCollection
    private List<String> references;
    private String questionImage;
    private String questionVideo;
    private String questionAudio;
    private String questionType;
    @Enumerated(EnumType.STRING)
    private Complexity complexity;
}
