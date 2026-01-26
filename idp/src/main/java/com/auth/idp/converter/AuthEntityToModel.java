package com.auth.idp.converter;

import com.auth.idp.entity.UserSession;
import com.auth.idp.model.UserSessionModel;
import com.elearning.elearning_sdk.converter.EntityToModel;
import org.springframework.stereotype.Component;

@Component("IdpEntityToModel")
public class AuthEntityToModel extends EntityToModel {

    public UserSessionModel toModel(UserSession userSession) {
        if (userSession == null) {
            return null;
        }
        return UserSessionModel.builder()
            .id(userSession.getId())
            .userId(userSession.getUserId())
            .token(userSession.getToken())
            .ipAddress(userSession.getIpAddress())
            .userAgent(userSession.getUserAgent())
            .active(userSession.isActive())
            .type(userSession.getType())
            .issuedAt(userSession.getIssuedAt())
            .expiredAt(userSession.getExpiredAt())
            .lastAccessAt(userSession.getLastAccessAt())
            .createdAt(userSession.getCreatedAt())
            .updatedAt(userSession.getUpdatedAt())
            .build();
    }
}
