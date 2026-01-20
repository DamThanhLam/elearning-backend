package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.SettingScope;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@Builder
public class SaveSettingModel {
    private ObjectId userId;
    private String name;
    private String group;
    private Object value;
    private SettingScope scope;
}
