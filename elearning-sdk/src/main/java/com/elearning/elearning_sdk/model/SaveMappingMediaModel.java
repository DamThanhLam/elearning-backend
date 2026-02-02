package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.MappingMediaType;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class SaveMappingMediaModel {
    private String mediaId;
    private String entityId;
    private MappingMediaType type;
}
