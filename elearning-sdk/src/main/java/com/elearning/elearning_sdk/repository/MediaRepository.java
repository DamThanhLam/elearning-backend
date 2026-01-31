package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.Media;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Repository
public interface MediaRepository extends ReactiveMongoRepository<Media, ObjectId> {
    Flux<Media> findByIdIn(Collection<ObjectId> ids);
}
