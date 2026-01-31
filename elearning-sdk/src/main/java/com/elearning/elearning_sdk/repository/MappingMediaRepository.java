package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.MappingMedia;
import com.elearning.elearning_sdk.entity.MappingMediaType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public interface MappingMediaRepository extends ReactiveMongoRepository<MappingMedia, ObjectId> {

    @Query(fields = "{ 'media_id': 1, '_id': 0 }")
    Flux<ObjectId> getMediaIdByEntityId(ObjectId entityId);

    Flux<MappingMedia> getMediaIdByEntityIdIn(Collection<ObjectId> entityIds);

    Mono<MappingMedia> findByType(MappingMediaType type);

    Mono<ObjectId> getFirstMediaIdByEntityIdAndType(ObjectId entityId, MappingMediaType type);

    Flux<ObjectId> getMediaIdByEntityIdAndType(ObjectId entityId, MappingMediaType type);
}
