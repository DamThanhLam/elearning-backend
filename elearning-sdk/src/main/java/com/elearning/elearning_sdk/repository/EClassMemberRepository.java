package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.EClassMember;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EClassMemberRepository extends ReactiveMongoRepository<EClassMember, ObjectId> {
    Mono<Boolean> existsByClassIdAndUserId(
        Mono<ObjectId> classId,
        Mono<ObjectId> userId
    );

    Mono<EClassMember> findByUserIdAndClassId(
        ObjectId userId,
        ObjectId classId
    );
}
