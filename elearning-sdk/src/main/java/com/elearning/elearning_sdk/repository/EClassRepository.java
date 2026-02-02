package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.EClass;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EClassRepository extends ReactiveMongoRepository<EClass, String> {
    Flux<EClass> findByTeacherId(String teacherId);
}
