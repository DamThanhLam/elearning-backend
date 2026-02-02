package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.MappingMediaType;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Builder
@Getter
public class MappingMediaModel {
    private String id;
    private String mediaId;
    private String entityId;
    private MappingMediaType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
