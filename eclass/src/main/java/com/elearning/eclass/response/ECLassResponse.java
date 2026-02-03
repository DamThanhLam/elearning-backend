package com.elearning.eclass.response;

import com.elearning.elearning_sdk.entity.EClassStatus;
import com.elearning.elearning_sdk.model.MediaModel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ECLassResponse {
    private String id;
    private MediaModel avatar;
    private String displayName;
    private String shortDescription;
    private long assignments;
    private long students;
    private EClassStatus status;
    private long createdAt;
    private long updatedAt;
}
