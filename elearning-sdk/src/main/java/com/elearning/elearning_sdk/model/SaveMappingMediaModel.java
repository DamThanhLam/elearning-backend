package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.MappingMediaType;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Builder
@Getter
public class SaveMappingMediaModel {
    private ObjectId mediaId;
    private ObjectId entityId;
    private MappingMediaType type;
}
