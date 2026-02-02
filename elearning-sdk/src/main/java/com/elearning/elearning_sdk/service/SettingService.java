package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.converter.ModelToEntity;
import com.elearning.elearning_sdk.entity.Setting;
import com.elearning.elearning_sdk.model.SaveSettingModel;
import com.elearning.elearning_sdk.model.SettingModel;
import com.elearning.elearning_sdk.repository.SettingRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;
    private final EntityToModel entityToModel;
    private final ModelToEntity modelToEntity;

    public Mono<String> save(SaveSettingModel model) {
        Setting entity = modelToEntity.toEntity(model);
        return settingRepository
            .save(entity)
            .map(Setting::getId);
    }

    public Mono<String> saveDataMetaUnique(SaveSettingModel model) {
        return settingRepository
            .findByUserIdAndGroupAndName(
                model.getUserId(),
                model.getGroup(),
                model.getName()
            )
            .switchIfEmpty(Mono.just(modelToEntity.toEntity(model)))
            .flatMap(entity -> {
                entity.setValue(model.getValue());
                return settingRepository.save(entity);
            })
            .map(Setting::getId);
    }

    public Flux<SettingModel> getSettingsByUserIdAndGroup(
        String userId,
        String group
    ) {
        return settingRepository
            .findByUserIdAndGroup(userId, group)
            .map(entityToModel::toModel);
    }

    public Mono<SettingModel> getSettingByUserIdAndGroupAndName(
        String userId,
        String group,
        String name
    ) {
        return settingRepository.findByUserIdAndGroupAndName(
            userId,
            group,
            name
        ).map(entityToModel::toModel);
    }
}
