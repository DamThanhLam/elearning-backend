package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.NotificationChannel;
import com.elearning.elearning_sdk.entity.NotificationStatus;
import com.elearning.elearning_sdk.entity.NotificationType;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class SaveNotificationModel {
    private String fromId;
    private String toUserId;
    private String title;
    private String content;
    private String url;
    private boolean read;
    private NotificationType type;
    private NotificationChannel channel;
    private NotificationStatus status;
}
