package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.Notification;
import com.elearning.elearning_sdk.entity.NotificationChannel;
import com.elearning.elearning_sdk.entity.NotificationStatus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface NotificationRepository extends ReactiveMongoRepository<Notification, ObjectId> {
    Mono<Integer> countNotificationsByToUserIdAndChannelAndStatus(
        ObjectId toUserId,
        NotificationChannel channel,
        NotificationStatus status
    );
}
