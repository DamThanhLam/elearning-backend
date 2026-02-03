package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

@Getter
@Setter
@TypeAlias("essay_question_answer")
public class EssayQuestionAnswer extends QuestionAnswer {
    String answer;
}
