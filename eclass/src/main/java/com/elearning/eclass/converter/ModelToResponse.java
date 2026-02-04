package com.elearning.eclass.converter;

import com.elearning.eclass.response.AssignmentResponse;
import com.elearning.eclass.response.ECLassResponse;
import com.elearning.eclass.response.ECLassStatisticResponse;
import com.elearning.elearning_sdk.model.AssignmentModel;
import com.elearning.elearning_sdk.model.EClassModel;
import com.elearning.elearning_sdk.model.MediaModel;
import com.elearning.elearning_sdk.util.ClockProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModelToResponse {

    private final ClockProxy clock;

    public ECLassResponse toResponse(
        EClassModel model,
        MediaModel avatar,
        Long students,
        Long assignments
    ) {
        return ECLassResponse.builder()
            .id(model.getId())
            .displayName(model.getDisplayName())
            .shortDescription(model.getShortDescription())
            .avatar(avatar)
            .assignments(assignments)
            .students(students)
            .createdAt(clock.toTimestamp(model.getCreatedAt()))
            .updatedAt(clock.toTimestamp(model.getUpdatedAt()))
            .status(model.getStatus())
            .build();
    }

    public AssignmentResponse toResponse(
        AssignmentModel model
    ) {
        if (model == null) {
            return null;
        }

        return AssignmentResponse.builder()
            .id(model.getId())
            .displayName(model.getDisplayName())
            .shortDescription(model.getShortDescription())
            .type(model.getType())
            .startAt(clock.toTimestamp(model.getStartAt()))
            .dueAt(clock.toTimestamp(model.getDueAt()))
            .status(model.getStatus())
            .createdAt(clock.toTimestamp(model.getCreatedAt()))
            .updatedAt(clock.toTimestamp(model.getUpdatedAt()))
            .build();
    }

    public ECLassStatisticResponse toECLassStatisticResponse(
        long totalStudents,
        long openAssignments,
        long upcomingAssignments
    ) {
        return ECLassStatisticResponse.builder()
            .totalAssignments(totalStudents)
            .openAssignments(openAssignments)
            .upcomingAssignments(upcomingAssignments)
            .build();
    }
}
