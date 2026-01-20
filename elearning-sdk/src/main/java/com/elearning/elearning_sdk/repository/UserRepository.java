package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, ObjectId> {
    Mono<User> findByEmail(String email);

    Mono<User> findUserByPhone(String phone);

    Mono<String> findEmailById(ObjectId userId);
}
