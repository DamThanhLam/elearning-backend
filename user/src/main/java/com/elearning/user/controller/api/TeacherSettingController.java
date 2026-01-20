package com.elearning.user.controller.api;

import com.elearning.elearning_sdk.annotation.AuthenticatedUserId;
import com.elearning.elearning_sdk.service.UserSettingService;
import com.elearning.user.request.SaveTeacherNotificationSettingRequest;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.elearning.elearning_sdk.constant.Constant.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TeacherSettingController {
    private final UserSettingService userSettingService;


    @PutMapping("/teachers/settings/notifications")
    public Mono<ResponseEntity<Object>> teacherSettingNotifications(
        @AuthenticatedUserId ObjectId userId,
        @RequestBody Mono<SaveTeacherNotificationSettingRequest> request
    ) {
        return request
            .flatMap(rq ->
                userSettingService.saveNotificationSettings(
                    userId,
                    Map.ofEntries(
                        Map.entry(
                            TEACHER_NOTIFICATION_SETTING_EMAIL_NEW_SUBMISSION,
                            rq.isNotificationEmailNewSubmission()
                        ),
                        Map.entry(
                            TEACHER_NOTIFICATION_SETTING_EMAIL_GRADE_UPDATE,
                            rq.isNotificationEmailGradeUpdate()
                        ),
                        Map.entry(
                            TEACHER_NOTIFICATION_SETTING_PUSH_NEW_SUBMISSION,
                            rq.isNotificationPushNewSubmission()
                        ),
                        Map.entry(
                            TEACHER_NOTIFICATION_SETTING_PUSH_GRADE_UPDATE,
                            rq.isNotificationPushGradeUpdate()
                        )
                    )
                )
            )
            .then(Mono.just(ResponseEntity.noContent().build()));
    }
}