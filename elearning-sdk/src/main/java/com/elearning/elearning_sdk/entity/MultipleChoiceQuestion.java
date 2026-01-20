package com.elearning.elearning_sdk.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@TypeAlias("multiple_choice_question")
public class MultipleChoiceQuestion extends Question {
    private List<Answer> answers;

    @Field(name = "correct_answer_ids")
    private List<ObjectId> correctAnswerIds;
}
