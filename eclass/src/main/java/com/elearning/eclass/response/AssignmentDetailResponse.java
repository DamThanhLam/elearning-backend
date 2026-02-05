package com.elearning.eclass.response;

import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.entity.AssignmentType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AssignmentDetailResponse {
    private String id;
    private String eclassId;
    private String displayName;
    private String shortDescription;
    private AssignmentType type;
    private long startAt;
    private long dueAt;
    private AssignmentStatus status;
}
