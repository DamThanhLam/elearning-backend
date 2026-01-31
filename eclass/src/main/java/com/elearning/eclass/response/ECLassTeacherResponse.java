package com.elearning.eclass.response;

import com.elearning.elearning_sdk.entity.EClassStatus;
import com.elearning.elearning_sdk.model.MediaModel;
import lombok.Builder;

@Builder
public class ECLassTeacherResponse {
    private String id;
    private MediaModel avatar;
    private String displayName;
    private EClassStatus status;
    private long updatedAt;
}
