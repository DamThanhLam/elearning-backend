package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.entity.AssignmentType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
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
