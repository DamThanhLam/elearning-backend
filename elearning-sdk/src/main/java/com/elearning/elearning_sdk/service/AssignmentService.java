package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.repository.AssignmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public Mono<Long> countAssignmentByEClassId(
        String eClassId
    ){
        return assignmentRepository.countByEclassIdAndStatus(
            eClassId,
            AssignmentStatus.OPEN
        );
    }
}
