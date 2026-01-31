package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.MappingMediaType;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Builder
@Getter
public class MappingMediaModel {
    private ObjectId id;
    private ObjectId mediaId;
    private ObjectId entityId;
    private MappingMediaType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
