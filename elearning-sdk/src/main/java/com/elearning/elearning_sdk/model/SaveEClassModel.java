package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.EClassStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SaveEClassModel {
    private String displayName;
    private String shortDescription;
    private String description;
    private EClassStatus status;
}
