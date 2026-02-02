package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.SettingScope;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class SaveSettingModel {
    private String userId;
    private String name;
    private String group;
    private Object value;
    private SettingScope scope;
}
