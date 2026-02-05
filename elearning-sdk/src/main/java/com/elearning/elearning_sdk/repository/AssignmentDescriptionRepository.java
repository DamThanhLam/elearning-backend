package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.AssignmentDescription;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AssignmentDescriptionRepository
    extends ReactiveMongoRepository<AssignmentDescription, String> {

    Mono<AssignmentDescription> getDescriptionByAssignmentId(String assignmentId);
}
