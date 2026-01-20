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
@TypeAlias("multiple_questions_assignment")
public class MultipleQuestionsAssignment extends Assignment {
    @Field(name = "question_ids")
    private List<ObjectId> questionIds;

    @Field(name = "question_count")
    private int questionCount;
}
