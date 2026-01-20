package com.auth.idp.converter;

import com.auth.idp.entity.UserSession;
import com.auth.idp.model.UserSessionModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("IdpModelToEntity")
public class ModelToEntity {

    public Mono<UserSession> toEntity(UserSessionModel model) {
        if (model == null) {
            return Mono.empty();
        }
        UserSession entity = new UserSession();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        entity.setToken(model.getToken());
        entity.setIpAddress(model.getIpAddress());
        entity.setUserAgent(model.getUserAgent());
        entity.setActive(model.isActive());
        entity.setType(model.getType());
        entity.setIssuedAt(model.getIssuedAt());
        entity.setExpiredAt(model.getExpiredAt());
        entity.setLastAccessAt(model.getLastAccessAt());
        return Mono.just(entity);
    }

}
