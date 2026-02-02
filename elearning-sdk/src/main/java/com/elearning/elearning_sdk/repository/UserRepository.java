package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.User;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByEmail(String email);

    Mono<User> findUserByPhone(String phone);

    Mono<String> findEmailById(String userId);
}
