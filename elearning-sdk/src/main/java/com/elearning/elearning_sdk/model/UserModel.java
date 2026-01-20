package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.UserRole;
import com.elearning.elearning_sdk.entity.UserStatus;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class UserModel {
    private ObjectId id;
    private String displayName;
    private String email;
    private boolean emailVerified;
    private String phone;
    private boolean phoneVerified;
    private List<UserRole> roles;
    private String password;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
