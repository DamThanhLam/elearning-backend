package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.EClassMemberStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class EClassMemberModel {
    private String id;
    private String classId;
    private String userId;
    private EClassMemberStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
