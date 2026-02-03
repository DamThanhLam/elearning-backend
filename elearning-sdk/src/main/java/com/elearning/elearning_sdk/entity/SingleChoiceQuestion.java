package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@TypeAlias("single_choice_question")
public class SingleChoiceQuestion extends Question {
    private List<Answer> answers;

    @Field(name = "correct_answer_id")
    private String correctAnswerId;
}
