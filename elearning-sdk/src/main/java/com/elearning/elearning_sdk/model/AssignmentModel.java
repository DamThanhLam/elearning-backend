package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.entity.AssignmentType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
public class AssignmentModel {
    private String id;
    private String eclassId;
    private String displayName;
    private String shortDescription;
    private AssignmentType type;
    private LocalDateTime startAt;
    private LocalDateTime dueAt;
    private AssignmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
