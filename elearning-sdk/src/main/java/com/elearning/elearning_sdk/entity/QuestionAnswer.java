package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "question_answers")
public class QuestionAnswer {

    @Id
    private String id;

    @Field("question_id")
    private QuestionId questionId;

    @CreatedDate
    @Field(name = "created_at")
    private LocalDateTime createdAt;
}
