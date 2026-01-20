package com.elearning.elearning_sdk.repository;

import com.elearning.elearning_sdk.entity.Setting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping
public interface SettingRepository extends ReactiveMongoRepository<Setting, ObjectId> {
    Flux<Setting> findByUserIdAndGroup(ObjectId userId, String group);

    Mono<Setting> findByUserIdAndGroupAndName(ObjectId userId, String group, String name);
}
