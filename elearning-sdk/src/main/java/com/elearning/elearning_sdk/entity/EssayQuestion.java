package com.elearning.elearning_sdk.entity;

import lombok.Builder;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("essay_question")
public class EssayQuestion extends Question {
    private String answer;
}
