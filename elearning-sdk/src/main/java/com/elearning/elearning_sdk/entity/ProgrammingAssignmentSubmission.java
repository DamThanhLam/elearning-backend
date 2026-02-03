package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@TypeAlias("programming_assignment_submission")
public class ProgrammingAssignmentSubmission extends AssignmentSubmission {

    @Field("programming_workload_id")
    private String ProgrammingWorkloadId;
}
