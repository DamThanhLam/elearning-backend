package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.Assignment;
import com.elearning.elearning_sdk.entity.AssignmentStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface AssignmentRepository extends ReactiveMongoRepository<Assignment, String> {
    Mono<Long> countByEclassIdAndStatus(String eclassId, AssignmentStatus status);

    Mono<Long> countByEclassId(String eclassId);

    Mono<Long> countByIdAndStartAtBetween(
        String id,
        LocalDateTime startAtAfter,
        LocalDateTime startAtBefore
    );
}
