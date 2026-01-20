package com.elearning.elearning_sdk.converter;

import com.elearning.elearning_sdk.entity.*;
import com.elearning.elearning_sdk.model.*;
import com.elearning.elearning_sdk.util.ClockProxy;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("ModelToEntity")
@AllArgsConstructor
@Primary
public class ModelToEntity {

    @Autowired
    private ClockProxy clock;

    public MappingMedia toEntity(SaveMappingMediaModel model) {
        MappingMedia entity = new MappingMedia();
        mergeToEntity(model, entity);
        entity.setCreatedAt(entity.getUpdatedAt());
        return entity;
    }

    public Media toEntity(SaveMediaModel model) {
        Media entity = new Media();
        mergeToEntity(model, entity);
        entity.setCreatedAt(entity.getUpdatedAt());
        return entity;
    }

    public Notification toEntity(SaveNotificationModel model) {
        Notification entity = new Notification();
        mergeToEntity(model, entity);
        entity.setCreatedAt(entity.getUpdatedAt());
        return entity;
    }

    public User toEntity(SaveUserModel model) {
        User entity = new User();
        mergeToEntity(model, entity);
        entity.setCreatedAt(entity.getUpdatedAt());
        return entity;
    }

    public Setting toEntity(SaveSettingModel model) {
        Setting entity = new Setting();
        mergeToEntity(model, entity);
        entity.setCreatedAt(entity.getUpdatedAt());
        return entity;
    }

    private void mergeToEntity(
        SaveSettingModel model,
        Setting entity
    ) {
        entity.setName(model.getName());
        entity.setScope(model.getScope());
        entity.setGroup(model.getGroup());
        entity.setValue(model.getValue());
        entity.setUserId(model.getUserId());
        entity.setUpdatedAt(clock.nowDateTime());
    }

    public void mergeToEntity(
        SaveUserModel model,
        User entity
    ) {
        entity.setDisplayName(model.getDisplayName());
        entity.setEmail(model.getEmail());
        entity.setPhone(model.getPhone());
        entity.setPassword(model.getPassword());
        entity.setRoles(model.getRoles());
        entity.setStatus(model.getStatus());
        entity.setUpdatedAt(clock.nowDateTime());
    }

    public void mergeToEntity(
        SaveNotificationModel model,
        Notification entity
    ) {
        entity.setFromId(model.getFromId());
        entity.setToUserId(model.getToUserId());
        entity.setTitle(model.getTitle());
        entity.setContent(model.getContent());
        entity.setUrl(model.getUrl());
        entity.setType(model.getType());
        entity.setChannel(model.getChannel());
        entity.setStatus(model.getStatus());
        entity.setUpdatedAt(clock.nowDateTime());
    }

    public void mergeToEntity(
        SaveMediaModel model,
        Media entity
    ) {
        entity.setName(model.getName());
        entity.setUrl(model.getUrl());
        entity.setMimeType(model.getMimeType());
        entity.setMediaType(model.getMediaType());
        entity.setOriginalName(model.getOriginalName());
        entity.setStatus(model.getStatus());
        entity.setUpdatedAt(clock.nowDateTime());
    }

    public void mergeToEntity(
        SaveMappingMediaModel model,
        MappingMedia entity
    ) {
        entity.setMediaId(model.getMediaId());
        entity.setEntityId(model.getEntityId());
        entity.setType(entity.getType());
        entity.setUpdatedAt(clock.nowDateTime());
    }

    public void mergeToEntity(
        SaveUserInformationModel model,
        User entity
    ) {
        entity.setDisplayName(model.getDisplayName());
        entity.setPhone(model.getPhone());
        entity.setUpdatedAt(entity.getUpdatedAt());
    }
}
