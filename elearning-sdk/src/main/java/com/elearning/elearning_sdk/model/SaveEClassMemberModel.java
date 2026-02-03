package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.EClassMemberStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SaveEClassMemberModel {
    private String classId;
    private String userId;
    private EClassMemberStatus status;
}
