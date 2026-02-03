package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@TypeAlias("single_choice_question_answer")
public class SingleChoiceQuestionAnswer extends QuestionAnswer {
    private List<Answer> answers;

    @Field(name = "answer_id")
    private String answerId;
}
