package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.converter.ModelToEntity;
import com.elearning.elearning_sdk.entity.Assignment;
import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.entity.MultipleQuestionsAssignment;
import com.elearning.elearning_sdk.entity.ProgrammingAssignment;
import com.elearning.elearning_sdk.exception.NotFoundException;
import com.elearning.elearning_sdk.model.AssignmentModel;
import com.elearning.elearning_sdk.model.SaveECLassAssignmentModel;
import com.elearning.elearning_sdk.repository.AssignmentRepository;
import com.elearning.elearning_sdk.util.ClockProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AssignmentService {

    private final ModelToEntity modelToEntity;
    private final ClockProxy clockProxy;
    private final AssignmentDescriptionService assignmentDescriptionService;
    private final AssignmentRepository assignmentRepository;
    private final EntityToModel entityToModel;

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

    public Mono<Void> updateAssignment(
        String id,
        SaveECLassAssignmentModel model
    ) {
        return getAssignmentByIdOrThrow(id)
            .flatMap(entity -> {
                modelToEntity.mergeToEntity(model, entity);
                return assignmentRepository.save(entity);
            })
            .flatMap(entity ->
                assignmentDescriptionService.save(
                    id,
                    model.getDescription()
                )
            );
    }

    public Mono<AssignmentModel> getAssignmentById(String id) {
        return assignmentRepository.findById(id)
            .flatMap(entity -> {
                if (entity instanceof MultipleQuestionsAssignment subEntity) {
                    return Mono.just(entityToModel.toModel(subEntity));
                }
                if (entity instanceof ProgrammingAssignment subEntity) {
                    return Mono.just(entityToModel.toModel(subEntity));
                }
                return Mono.empty();
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

    private Mono<Assignment> getAssignmentByIdOrThrow(
        String id
    ) {
        return assignmentRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("assignment")));
    }
}
