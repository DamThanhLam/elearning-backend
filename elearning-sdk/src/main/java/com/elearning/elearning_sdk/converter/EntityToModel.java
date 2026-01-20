package com.elearning.elearning_sdk.converter;


import com.elearning.elearning_sdk.entity.*;
import com.elearning.elearning_sdk.model.*;
import com.elearning.elearning_sdk.util.ClockProxy;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("EntityToModel")
@AllArgsConstructor
@Primary
public class EntityToModel {

    @Autowired
    private ClockProxy clockProxy;

    public MediaModel toModel(Media entity) {
        if (entity == null) {
            return null;
        }
        return MediaModel.builder()
            .id(entity.getId())
            .url(entity.getUrl())
            .name(entity.getName())
            .originalName(entity.getOriginalName())
            .mediaType(entity.getMediaType())
            .mimeType(entity.getMimeType())
            .build();
    }

    public NotificationModel toModel(Notification entity) {
        if (entity == null) {
            return null;
        }
        return NotificationModel.builder()
            .id(entity.getId())
            .fromId(entity.getFromId())
            .toUserId(entity.getToUserId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .url(entity.getUrl())
            .status(entity.getStatus())
            .type(entity.getType())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    public UserModel toModel(User user) {
        if (user == null) {
            return null;
        }
        return UserModel.builder()
            .id(user.getId())
            .displayName(user.getDisplayName())
            .email(user.getEmail())
            .emailVerified(user.isEmailVerified())
            .phone(user.getPhone())
            .phoneVerified(user.isPhoneVerified())
            .roles(user.getRoles())
            .password(user.getPassword())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }

    public MappingMediaModel toModel(MappingMedia entity) {
        if (entity == null) {
            return null;
        }
        return MappingMediaModel.builder()
            .id(entity.getId())
            .entityId(entity.getEntityId())
            .mediaId(entity.getMediaId())
            .type(entity.getType())
            .secondaryKey(entity.getSecondaryKey())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    public SettingModel toModel(Setting entity) {
        if (entity == null) {
            return null;
        }
        return SettingModel.builder()
            .id(entity.getId())
            .userId(entity.getUserId())
            .value(entity.getValue())
            .scope(entity.getScope())
            .group(entity.getGroup())
            .createdAt(
                clockProxy.toTimestamp(entity.getCreatedAt())
            )
            .updatedAt(
                clockProxy.toTimestamp(entity.getUpdatedAt())
            )
            .build();
    }

    public UserInformationModel toUserInformationModel(User entity) {
        if (entity == null) {
            return null;
        }
        return UserInformationModel.builder()
            .id(entity.getId())
            .displayName(entity.getDisplayName())
            .email(entity.getEmail())
            .phone(entity.getPhone())
            .roles(entity.getRoles())
            .build();
    }

    public MappingMediaModel toMappingMediaModel(MappingMedia entity) {
        if (entity == null) {
            return null;
        }
        return MappingMediaModel.builder()
            .mediaId(entity.getMediaId())
            .entityId(entity.getEntityId())
            .secondaryKey(entity.getSecondaryKey())
            .type(entity.getType())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
}
