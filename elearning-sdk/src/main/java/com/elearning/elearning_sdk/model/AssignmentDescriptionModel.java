package com.elearning.elearning_sdk.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AssignmentDescriptionModel {
    private String id;
    private String assignmentId;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
