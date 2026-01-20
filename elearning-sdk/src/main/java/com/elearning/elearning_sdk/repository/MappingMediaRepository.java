package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.MappingMedia;
import com.elearning.elearning_sdk.entity.MappingMediaType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface MappingMediaRepository extends ReactiveMongoRepository<MappingMedia, ObjectId> {

    @Query(value = "{ 'entityId': ?0 }", fields = "{ 'mediaId': 1, '_id': 0 }")
    Flux<ObjectId> getMediaIdByEntityId(ObjectId entityId);

    Mono<MappingMedia> findByType(MappingMediaType type);
}
