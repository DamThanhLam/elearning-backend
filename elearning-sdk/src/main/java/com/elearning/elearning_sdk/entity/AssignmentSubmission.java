package com.elearning.elearning_sdk.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "assignment_submissions")
public class AssignmentSubmission {

    @Id
    private String id;

    @Field("assignment_id")
    private String assignmentId;

    @Field("user_id")
    private String userId;

    private BigDecimal score;

    private AssignmentSubmissionStatus status;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
