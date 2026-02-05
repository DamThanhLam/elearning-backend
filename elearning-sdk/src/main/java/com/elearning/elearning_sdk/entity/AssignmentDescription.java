package com.elearning.elearning_sdk.entity;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document(collection = "assignment_description")
public class AssignmentDescription {
    @Id
    @Field(name = "assignment_id")
    private String assignmentId;

    private String description;
}
