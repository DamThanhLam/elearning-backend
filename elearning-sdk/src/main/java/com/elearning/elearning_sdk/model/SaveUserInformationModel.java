package com.elearning.elearning_sdk.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SaveUserInformationModel {
    private String displayName;
    private String phone;
}
