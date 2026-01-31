package com.elearning.eclass.converter;

import com.elearning.eclass.response.ECLassTeacherResponse;
import com.elearning.elearning_sdk.model.EClassModel;
import com.elearning.elearning_sdk.model.MediaModel;
import com.elearning.elearning_sdk.util.ClockProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModelToResponse {

    private final ClockProxy clock;

    public ECLassTeacherResponse toModel(
        EClassModel model,
        MediaModel avatar
    ) {
        return ECLassTeacherResponse.builder()
            .id(model.getId().toHexString())
            .displayName(model.getDisplayName())
            .avatar(avatar)
            .updatedAt(clock.toTimestamp(model.getUpdatedAt()))
            .status(model.getStatus())
            .build();
    }
}
