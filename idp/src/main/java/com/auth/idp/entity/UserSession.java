package com.auth.idp.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "user_sessions")
public class UserSession {

    @Id
    private ObjectId id;

    @Field(name = "user_id")
    private ObjectId userId;

    private String token;

    @Field(name = "ip_address")
    private String ipAddress;

    @Field(name = "user_agent")
    private String userAgent;

    private boolean active;

    private UserSessionType type;

    @Field(name = "issued_at")
    private LocalDateTime issuedAt;

    @Field(name = "expired_at")
    private LocalDateTime expiredAt;

    @Field(name = "last_access_at")
    private LocalDateTime lastAccessAt;

    @CreatedDate
    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private LocalDateTime updatedAt;
}
