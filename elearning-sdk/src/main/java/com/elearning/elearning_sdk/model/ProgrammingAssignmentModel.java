package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.ProgrammingLanguage;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ProgrammingAssignmentModel extends AssignmentModel {
    private ProgrammingLanguage language;
    private String languageVersion;
}
