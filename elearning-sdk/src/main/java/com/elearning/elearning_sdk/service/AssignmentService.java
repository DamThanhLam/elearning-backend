package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.repository.AssignmentRepository;
import com.elearning.elearning_sdk.util.ClockProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AssignmentService {

    private final ClockProxy clockProxy;
    private final AssignmentRepository assignmentRepository;

    public Mono<Long> countAssignmentByEClassIdByStatus(
        String eClassId,
        AssignmentStatus status
    ){
        return assignmentRepository.countByEclassIdAndStatus(
            eClassId,
            AssignmentStatus.OPEN
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
