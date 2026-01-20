package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "settings")
@Getter
@Setter
public class Setting {
    @Id
    private ObjectId id;

    private ObjectId userId;

    private String name;

    private String group;

    private Object value;

    private SettingScope scope;

    @CreatedDate
    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private LocalDateTime updatedAt;

    public <T> T getValue(Class<T> clazz) {
        return clazz.cast(value);
    }
}
