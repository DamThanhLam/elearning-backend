package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.SettingScope;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class SettingModel {
    private String id;
    private String userId;
    private String name;
    private String group;
    private Object value;
    private SettingScope scope;
    private long createdAt;
    private long updatedAt;
    public <T> T getValue(Class<T> clazz) {
        return clazz.cast(value);
    }
}
