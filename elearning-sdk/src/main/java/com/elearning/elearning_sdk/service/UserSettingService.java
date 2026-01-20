package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.converter.ModelToEntity;
import com.elearning.elearning_sdk.model.SaveSettingModel;
import com.elearning.elearning_sdk.model.SettingModel;
import com.elearning.elearning_sdk.repository.SettingRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.elearning.elearning_sdk.constant.Constant.*;

@Service
public class UserSettingService extends SettingService {

    public UserSettingService(
        SettingRepository settingRepository,
        EntityToModel entityToModel,
        ModelToEntity modelToEntity
    ) {
        super(
            settingRepository,
            entityToModel,
            modelToEntity
        );
    }

    public Mono<Void> saveNotificationSettings(
        ObjectId userId,
        Map<String, Object> metaValueMapByMetaKey
    ) {
        return Flux
            .fromIterable(metaValueMapByMetaKey.entrySet())
            .flatMap(entry ->
                saveDataMetaUnique(
                    buildSaveSettingModel(
                        userId,
                        NOTIFICATION_SETTINGS,
                        entry.getKey(),
                        entry.getValue().toString()
                    )
                )
            )
            .then();
    }

    public Flux<SettingModel> getUserNotificationSettings(
        ObjectId userId
    ) {
        return getSettingsByUserIdAndGroup(
            userId,
            NOTIFICATION_SETTINGS
        );
    }

    public Mono<String> getTeacherNotificationEmailNewSubmissionOrDefault(
        ObjectId userId,
        String defaultValue
    ) {
        return getSettingByUserIdAndGroupAndName(
            userId,
            NOTIFICATION_SETTINGS,
            TEACHER_NOTIFICATION_SETTING_EMAIL_NEW_SUBMISSION
        )
            .map((model) -> model.getValue(String.class))
            .defaultIfEmpty(defaultValue);
    }

    public Mono<String> getTeacherNotificationEmailGradeUpdateOrDefault(
        ObjectId userId,
        String defaultValue
    ) {
        return getSettingByUserIdAndGroupAndName(
            userId,
            NOTIFICATION_SETTINGS,
            TEACHER_NOTIFICATION_SETTING_EMAIL_GRADE_UPDATE
        )
            .map((model) -> model.getValue(String.class))
            .defaultIfEmpty(defaultValue);
    }

    public Mono<String> getTeacherNotificationPushNewSubmissionOrDefault(
        ObjectId userId,
        String defaultValue
    ) {
        return getSettingByUserIdAndGroupAndName(
            userId,
            NOTIFICATION_SETTINGS,
            TEACHER_NOTIFICATION_SETTING_PUSH_NEW_SUBMISSION
        )
            .map((model) -> model.getValue(String.class))
            .defaultIfEmpty(defaultValue);
    }

    public Mono<String> getTeacherNotificationPushGradeUpdateOrDefault(
        ObjectId userId,
        String defaultValue
    ) {
        return getSettingByUserIdAndGroupAndName(
            userId,
            NOTIFICATION_SETTINGS,
            TEACHER_NOTIFICATION_SETTING_PUSH_GRADE_UPDATE
        )
            .map((model) -> model.getValue(String.class))
            .defaultIfEmpty(defaultValue);
    }

    public Mono<String> getStudentNotificationPushNewSubmissionOrDefault(
        ObjectId userId,
        String defaultValue
    ) {
        return getSettingByUserIdAndGroupAndName(
            userId,
            NOTIFICATION_SETTINGS,
            STUDENT_NOTIFICATION_SETTING_PUSH_NEW_SUBMISSION
        )
            .map((model) -> model.getValue(String.class))
            .defaultIfEmpty(defaultValue);
    }

    public Mono<String> getStudentNotificationPushFeedbackOrDefault(
        ObjectId userId,
        String defaultValue
    ) {
        return getSettingByUserIdAndGroupAndName(
            userId,
            NOTIFICATION_SETTINGS,
            STUDENT_NOTIFICATION_SETTING_PUSH_FEEDBACK
        )
            .map((model) -> model.getValue(String.class))
            .defaultIfEmpty(defaultValue);
    }

    private SaveSettingModel buildSaveSettingModel(
        ObjectId userId,
        String group,
        String name,
        String value
    ) {
        return SaveSettingModel.builder()
            .userId(userId)
            .group(group)
            .name(name)
            .value(value)
            .build();
    }
}
