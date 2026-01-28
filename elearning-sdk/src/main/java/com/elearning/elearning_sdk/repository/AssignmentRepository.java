package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.Assignment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends MongoRepository<Assignment, ObjectId> {
}
