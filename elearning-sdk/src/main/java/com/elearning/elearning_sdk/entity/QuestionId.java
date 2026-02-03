package com.elearning.elearning_sdk.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class QuestionId {
    @Field("question_id")
    private String questionId;

    private long version;
}
