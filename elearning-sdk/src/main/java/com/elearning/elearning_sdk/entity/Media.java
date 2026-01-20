package com.elearning.elearning_sdk.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "medias")
public class Media {
    @Id
    private ObjectId id;

    private String url;

    private String name;

    @Field(name = "original_name")
    private String originalName;

    @Field(name = "media_type")
    private String mediaType;

    @Field(name = "mime_type")
    private String mimeType;

    private MediaStatus status;

    @CreatedDate
    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private LocalDateTime updatedAt;
}
