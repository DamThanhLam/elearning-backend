package com.elearning.elearning_sdk.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;

    @Field(name = "display_name")
    private String displayName;

    @Indexed(unique = true)
    private String email;

    @Field(name = "email_verified")
    private boolean emailVerified;

    private String phone;

    @Field(name = "phone_verified")
    private boolean phoneVerified;

    private List<UserRole> roles;

    private String password;

    private UserStatus status;

    @CreatedDate
    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private LocalDateTime updatedAt;
}
