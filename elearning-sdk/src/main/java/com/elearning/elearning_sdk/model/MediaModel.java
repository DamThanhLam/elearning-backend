package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.MediaStatus;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Builder
@Getter
public class MediaModel {
    private ObjectId id;
    private String url;
    private String name;
    private String originalName;
    private String mediaType;
    private String mimeType;
    private MediaStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
