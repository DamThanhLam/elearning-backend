package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.Notification;
import com.elearning.elearning_sdk.entity.NotificationChannel;
import com.elearning.elearning_sdk.entity.NotificationStatus;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface NotificationRepository extends ReactiveMongoRepository<Notification, String> {
    Mono<Integer> countNotificationsByToUserIdAndChannelAndStatus(
        String toUserId,
        NotificationChannel channel,
        NotificationStatus status
    );
}
