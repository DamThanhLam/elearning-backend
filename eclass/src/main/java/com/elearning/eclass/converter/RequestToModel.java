package com.elearning.eclass.converter;

import com.elearning.eclass.request.SaveEClassRequest;
import com.elearning.elearning_sdk.model.SaveEClassModel;
import org.springframework.stereotype.Component;

@Component
public class RequestToModel {

    public SaveEClassModel toModel(SaveEClassRequest request) {
        return SaveEClassModel.builder()
            .displayName(request.getDisplayName())
            .shortDescription(request.getShortDescription())
            .description(request.getDescription())
            .status(request.getStatus())
            .build();
    }
}
