package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.Assignment;
import com.elearning.elearning_sdk.entity.ProgrammingAssignment;
import com.elearning.elearning_sdk.entity.ProgrammingLanguage;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class SaveProgrammingAssignmentModel extends SaveECLassAssignmentModel {
    private ProgrammingLanguage language;
    private String languageVersion;

    @Override
    public Assignment getEntity() {
        ProgrammingAssignment entity = new ProgrammingAssignment();
        entity.setLanguage(language);
        entity.setLanguageVersion(languageVersion);
        return entity;
    }
}
