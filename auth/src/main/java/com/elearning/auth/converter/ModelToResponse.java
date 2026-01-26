package com.elearning.auth.converter;

import com.elearning.auth.response.UserInformationResponse;
import com.elearning.elearning_sdk.model.UserInformationModel;
import org.springframework.stereotype.Component;

@Component
public class ModelToResponse {

    public UserInformationResponse toResponse(
        UserInformationModel model
    ) {
        return UserInformationResponse.builder()
            .displayName(model.getDisplayName())
            .roles(model.getRoles())
            .build();
    }
}
