package com.elearning.elearning_sdk.entity;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "assignment_description")
public class AssignmentDescription {
    @Id
    private String id;

    @Indexed
    @Field(name = "assignment_id")
    private String assignmentId;

    private String description;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
