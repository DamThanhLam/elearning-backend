package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.converter.ModelToEntity;
import com.elearning.elearning_sdk.entity.Assignment;
import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.model.SaveECLassAssignmentModel;
import com.elearning.elearning_sdk.repository.AssignmentRepository;
import com.elearning.elearning_sdk.util.ClockProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ECLassAssignmentService {

    private final ModelToEntity modelToEntity;
    private final ClockProxy clockProxy;
    private final AssignmentDescriptionService assignmentDescriptionService;
    private final AssignmentRepository assignmentRepository;

    public Mono<String> addAssignment(
        String eClassId,
        SaveECLassAssignmentModel model
    ) {
        Assignment entity = model.getEntity();
        modelToEntity.mergeToEntity(model, entity);
        entity.setEclassId(eClassId);
        entity.setCreatedAt(entity.getUpdatedAt());
        return assignmentRepository.save(entity)
            .flatMap(e -> {
                String eclassId = e.getEclassId();
                return assignmentDescriptionService
                    .save(
                        eclassId,
                        model.getDescription()
                    )
                    .thenReturn(eclassId);
            });
    }

    public Mono<Long> countAssignmentByEClassIdByStatus(
        String eClassId,
        AssignmentStatus status
    ){
        return assignmentRepository.countByEclassIdAndStatus(
            eClassId,
            status
        );
    }

    public Mono<Long> countTotalAssignmentByEClassId(String id) {
        return assignmentRepository.countByEclassId(id);
    }

    public Mono<Long> countUpcomingAssignments(
        String id,
        long daysAhead
    ) {
        LocalDateTime fromDate = clockProxy.nowDateTime();
        LocalDateTime toDate = fromDate.plusDays(daysAhead);
        return assignmentRepository.countByIdAndStartAtBetween(
            id,
            fromDate,
            toDate
        );
    }
}
