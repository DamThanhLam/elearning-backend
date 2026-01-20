package com.elearning.user.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveTeacherNotificationSettingRequest {
    private boolean notificationEmailNewSubmission;
    private boolean notificationEmailGradeUpdate;
    private boolean notificationPushNewSubmission;
    private boolean notificationPushGradeUpdate;
}
