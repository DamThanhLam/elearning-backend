package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.EClass;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface EClassRepository extends ReactiveMongoRepository<EClass, ObjectId> {
    Flux<List<EClass>> findByTeacherId(ObjectId teacherId);
}
