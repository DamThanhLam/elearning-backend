package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.NotificationChannel;
import com.elearning.elearning_sdk.entity.NotificationStatus;
import com.elearning.elearning_sdk.entity.NotificationType;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Builder
@Getter
public class SaveNotificationModel {
    private ObjectId fromId;
    private ObjectId toUserId;
    private String title;
    private String content;
    private String url;
    private boolean read;
    private NotificationType type;
    private NotificationChannel channel;
    private NotificationStatus status;
}
