package com.elearning.eclass.request;

import com.elearning.elearning_sdk.entity.EClassStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveEClassRequest {
    private String displayName;
    private String shortDescription;
    private String description;
    private EClassStatus status;
}
