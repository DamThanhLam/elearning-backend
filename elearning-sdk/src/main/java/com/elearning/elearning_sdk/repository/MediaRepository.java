package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.Media;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends ReactiveMongoRepository<Media, ObjectId> {
}
