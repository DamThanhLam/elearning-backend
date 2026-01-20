package com.elearning.elearning_sdk.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Data
@Document(collection = "questions")
public class Question {
    @Id
    private ObjectId id;

    @Indexed
    @Field(name = "tag_ids")
    private List<ObjectId> tagIds;

    @Indexed
    @Field(name = "category_ids")
    private List<ObjectId> categoryIds;

    private QuestionType type;

    private String name;

    private String note;
}
