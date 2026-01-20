package com.auth.idp.service;

import com.auth.idp.converter.EntityToModel;
import com.auth.idp.converter.ModelToEntity;
import com.auth.idp.entity.UserSession;
import com.auth.idp.model.UserSessionModel;
import com.auth.idp.repository.UserSessionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserSessionService {

    private final EntityToModel entityToModel;
    private final ModelToEntity modelToEntity;
    private final UserSessionRepository userSessionRepository;

    public UserSessionService(
        @Qualifier("IdpEntityToModel") EntityToModel entityToModel,
        @Qualifier("IdpModelToEntity") ModelToEntity modelToEntity,
        UserSessionRepository userSessionRepository
    ) {
        this.entityToModel = entityToModel;
        this.modelToEntity = modelToEntity;
        this.userSessionRepository = userSessionRepository;
    }

    public Mono<Void> addUserSession(UserSessionModel model) {
        Mono<UserSession> entity = modelToEntity.toEntity(model);
        return entity.flatMap(userSessionRepository::save)
            .then();
    }

    public Mono<UserSessionModel> getUserSessionByToken(String token) {
        return userSessionRepository.getUserSessionByToken(token)
            .map(entityToModel::toModel);
    }

    public Mono<List<UserSessionModel>> getUserSessionByUserIdAndActive(
        ObjectId userId,
        boolean active
    ) {
        return userSessionRepository
            .getUserSessionsByUserIdAndActive(userId, active)
            .map(list ->
                list.stream()
                    .map(entityToModel::toModel)
                    .toList()
            );
    }
}
