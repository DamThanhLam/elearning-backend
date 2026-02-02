package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.MappingMedia;
import com.elearning.elearning_sdk.entity.MappingMediaType;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public interface MappingMediaRepository extends ReactiveMongoRepository<MappingMedia, String> {

    @Query(fields = "{ 'media_id': 1, '_id': 0 }")
    Flux<String> getMediaIdByEntityId(String entityId);

    Flux<MappingMedia> getMediaIdByEntityIdIn(Collection<String> entityIds);

    Mono<MappingMedia> findByType(MappingMediaType type);

    Mono<String> getFirstMediaIdByEntityIdAndType(String entityId, MappingMediaType type);

    Flux<String> getMediaIdByEntityIdAndType(String entityId, MappingMediaType type);
}
