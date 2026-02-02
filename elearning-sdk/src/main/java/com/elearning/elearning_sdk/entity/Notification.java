package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.lang.String;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;

    @Indexed
    @Field(name = "from_id")
    private String fromId;

    @Indexed
    @Field(name = "to_user_id")
    private String toUserId;

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
