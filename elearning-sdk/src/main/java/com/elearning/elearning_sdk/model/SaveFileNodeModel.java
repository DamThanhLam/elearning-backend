package com.elearning.elearning_sdk.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class SaveFileNodeModel extends SaveTreeNodeModel {
    private String bucket;
    private String key;
    private String mimeType;
    private String storageProvider;
    private String checksum;
}
