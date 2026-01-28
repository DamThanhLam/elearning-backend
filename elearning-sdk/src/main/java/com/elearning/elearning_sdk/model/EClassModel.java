package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.EClassStatus;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Builder
@Getter
public class EClassModel {
    private ObjectId id;
    private ObjectId teacherId;
    private String displayName;
    private String shortDescription;
    private EClassStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
