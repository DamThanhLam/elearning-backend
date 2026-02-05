package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.EClassDescription;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EClassDescriptionRepository
    extends ReactiveMongoRepository<EClassDescription, String> {

    Mono<EClassDescription> getDescriptionByEclassId(String eclassId);
}
