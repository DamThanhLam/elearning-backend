package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.UserRole;
import com.elearning.elearning_sdk.entity.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SaveUserModel {
    private String displayName;
    private String email;
    private boolean emailVerified;
    private String phone;
    private List<UserRole> roles;
    private String password;
    private UserStatus status;
}
