package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.Assignment;
import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.entity.AssignmentType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public abstract class SaveECLassAssignmentModel {
    private String displayName;
    private String description;
    private String shortDescription;
    private AssignmentType type;
    private long startAt;
    private long dueAt;
    private AssignmentStatus status;

    public Assignment getEntity() {
        return null;
    }
}
