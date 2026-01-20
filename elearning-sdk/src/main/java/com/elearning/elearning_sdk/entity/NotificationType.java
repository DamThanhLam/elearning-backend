package com.elearning.elearning_sdk.entity;

import java.util.HashMap;
import java.util.Map;

public enum NotificationType {
    NEW_SUBMISSION,
    GRADE_UPDATED,
    SYSTEM,
    FEEDBACK,;

    private static final Map<NotificationType, String> queueNameMapNotificationType = new HashMap<>();
    static {
        queueNameMapNotificationType.put(NEW_SUBMISSION, "/new-submission");
        queueNameMapNotificationType.put(GRADE_UPDATED, "/grade-updated");
        queueNameMapNotificationType.put(SYSTEM, "/system");
        queueNameMapNotificationType.put(FEEDBACK, "/assignment-submissions/feedback");
    }

    public boolean isPrivate() {
        return this == NEW_SUBMISSION || this == GRADE_UPDATED;
    }

    public String getQueueName() {
        return queueNameMapNotificationType.get(this);
    }
}
