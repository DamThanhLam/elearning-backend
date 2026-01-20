package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "classes")
public class EClass {

    @Id
    private ObjectId id;

    @Indexed
    @Field(name = "teacher_id")
    private ObjectId teacherId;

    @TextIndexed
    @Field(name = "display_name")
    private String displayName;

    @Field(name = "short_description")
    private String shortDescription;

    private EClassStatus status;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
