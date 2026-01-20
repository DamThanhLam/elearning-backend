package com.auth.idp.model;

import com.auth.idp.entity.UserSessionType;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserSessionModel {
    private ObjectId id;
    private ObjectId userId;
    private String token;
    private String ipAddress;
    private String userAgent;
    private boolean active;
    private UserSessionType type;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
    private LocalDateTime lastAccessAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
