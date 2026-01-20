package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;

@Builder
@Getter
public class UserInformationModel {
    private ObjectId id;
    private String displayName;
    private String email;
    private String phone;
    private List<UserRole> roles;
}
