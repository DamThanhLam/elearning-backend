package com.elearning.elearning_sdk.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class FileNodeModel extends TreeNodeModel {
    private String originalName;
    private String bucket;
    private String key;
    private String mimeType;
    private String storageProvider;
    private String checksum;
}
