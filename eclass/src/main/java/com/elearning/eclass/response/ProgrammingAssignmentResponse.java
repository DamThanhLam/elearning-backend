package com.elearning.eclass.response;

import com.elearning.elearning_sdk.entity.ProgrammingLanguage;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ProgrammingAssignmentResponse extends AssignmentDetailResponse {
    private ProgrammingLanguage language;
    private String languageVersion;
}
