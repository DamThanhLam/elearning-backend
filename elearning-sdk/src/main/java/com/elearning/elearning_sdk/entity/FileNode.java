package com.elearning.elearning_sdk.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileNode extends TreeNode {
    private String bucket;

    private String key;

    @Field(name = "mime_type")
    private String mimeType;

    @Field(name = "storage_provider")
    private String storageProvider;

    private String checksum;
}
