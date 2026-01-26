package com.elearning.auth.response;

import com.elearning.elearning_sdk.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserInformationResponse {
    private String displayName;
    private List<UserRole> roles;
}
