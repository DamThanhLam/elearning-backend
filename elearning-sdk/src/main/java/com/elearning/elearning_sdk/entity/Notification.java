package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "notifications")
public class Notification {
    @Id
    private ObjectId id;

    @Indexed
    @Field(name = "from_id")
    private ObjectId fromId;

    @Indexed
    @Field(name = "to_user_id")
    private ObjectId toUserId;

    private String title;

    private String content;

    private NotificationType type;

    private NotificationChannel channel;

    private String url;

    private NotificationStatus status;

    @CreatedDate
    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private LocalDateTime updatedAt;
}
