package com.elearning.eclass.response;

import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.entity.AssignmentType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AssignmentResponse {
    private String id;
    private String displayName;
    private String shortDescription;
    private AssignmentType type;
    private long startAt;
    private long dueAt;
    private AssignmentStatus status;
    private long createdAt;
    private long updatedAt;
}
