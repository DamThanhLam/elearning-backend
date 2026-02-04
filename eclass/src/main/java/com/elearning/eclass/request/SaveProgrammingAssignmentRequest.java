package com.elearning.eclass.request;

import com.elearning.elearning_sdk.entity.ProgrammingLanguage;
import com.elearning.elearning_sdk.model.SaveECLassAssignmentModel;
import com.elearning.elearning_sdk.model.SaveProgrammingAssignmentModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveProgrammingAssignmentRequest extends SaveAssignmentRequest {
    private ProgrammingLanguage language;
    private String languageVersion;

    @Override
    public SaveECLassAssignmentModel toModel() {
        var builder = SaveProgrammingAssignmentModel.builder()
            .language(language)
            .languageVersion(languageVersion);

        applyCommonFields(builder);

        return builder.build();
    }
}
