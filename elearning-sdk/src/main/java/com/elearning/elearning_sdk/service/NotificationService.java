package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.converter.ModelToEntity;
import com.elearning.elearning_sdk.entity.Notification;
import com.elearning.elearning_sdk.entity.NotificationChannel;
import com.elearning.elearning_sdk.entity.NotificationStatus;
import com.elearning.elearning_sdk.exception.NotFoundException;
import com.elearning.elearning_sdk.model.NotificationModel;
import com.elearning.elearning_sdk.model.SaveNotificationModel;
import com.elearning.elearning_sdk.repository.NotificationRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelToEntity modelToEntity;
    private final EntityToModel entityToModel;

    public Mono<String> addNotification(SaveNotificationModel model) {
        Notification entity = modelToEntity.toEntity(model);
        return notificationRepository.save(entity)
            .map(Notification::getId);
    }

    public Mono<Void> updateNotificationStatus(String id, NotificationStatus status) {
        return getNotificationByIdOrThrow(id)
            .map(entity -> {
                entity.setStatus(status);
                return entity;
            })
            .flatMap(notificationRepository::save)
            .then();
    }

    public Mono<NotificationModel> getNotificationById(String id) {
        return notificationRepository.findById(id)
            .map(entityToModel::toModel);
    }

    public Mono<Integer> countNotificationsByStatusNotRead(
        String toUserId,
        NotificationChannel channel
    ) {
        return notificationRepository.countNotificationsByToUserIdAndChannelAndStatus(
            toUserId,
            channel,
            NotificationStatus.READ
        );
    }

    private Mono<Notification> getNotificationByIdOrThrow(String id) {
        return notificationRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("notification")));
    }
}
