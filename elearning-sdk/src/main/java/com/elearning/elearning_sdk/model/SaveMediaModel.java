package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.MediaStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SaveMediaModel {
    private String url;
    private String name;
    private String originalName;
    private String mediaType;
    private String mimeType;
    private MediaStatus status;
}
