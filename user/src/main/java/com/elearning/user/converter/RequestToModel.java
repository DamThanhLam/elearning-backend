package com.elearning.user.converter;

import com.elearning.elearning_sdk.entity.UserRole;
import com.elearning.elearning_sdk.entity.UserStatus;
import com.elearning.elearning_sdk.model.SaveUserInformationModel;
import com.elearning.elearning_sdk.model.SaveUserModel;
import com.elearning.user.request.SaveUserInformationRequest;
import com.elearning.user.request.SaveUserRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class RequestToModel {

    public SaveUserModel toModel(
        SaveUserRequest request,
        String password
    ) {
        String savePassword = password.isEmpty() ? request.getPassword() : password;
        return SaveUserModel.builder()
            .displayName(request.getDisplayName())
            .email(request.getEmail())
            .phone(request.getPhone())
            .roles(Collections.singletonList(
                UserRole.valueOf(request.getRole())
            ))
            .password(savePassword)
            .status(UserStatus.NOT_VERIFIED)
            .build();
    }

    public SaveUserInformationModel toModel(
        SaveUserInformationRequest request
    ) {
        return SaveUserInformationModel.builder()
            .displayName(request.getDisplayName())
            .phone(request.getPhone())
            .build();
    }
}
