package com.auth.idp.repository;

import com.auth.idp.entity.UserSession;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface UserSessionRepository extends ReactiveMongoRepository<UserSession, ObjectId> {

    Mono<UserSession> getUserSessionByToken(String token);

    Mono<List<UserSession>> getUserSessionsByUserIdAndActive(
        ObjectId userId,
        boolean active
    );
}
