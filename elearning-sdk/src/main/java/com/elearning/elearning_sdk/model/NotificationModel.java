package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.NotificationStatus;
import com.elearning.elearning_sdk.entity.NotificationType;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Builder
@Getter
public class NotificationModel {
    private String id;
    private String fromId;
    private String toUserId;
    private String title;
    private String content;
    private String url;
    private boolean read;
    private NotificationType type;
    private NotificationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
