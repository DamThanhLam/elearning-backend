package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.EClassMember;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EClassMemberRepository extends ReactiveMongoRepository<EClassMember, String> {
    Mono<Boolean> existsByClassIdAndUserId(
        Mono<String> classId,
        Mono<String> userId
    );

    Mono<EClassMember> findByUserIdAndClassId(
        String userId,
        String classId
    );
}
