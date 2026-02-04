package com.elearning.eclass.request;

import com.elearning.elearning_sdk.model.SaveECLassAssignmentModel;
import com.elearning.elearning_sdk.model.SaveMultipleQuestionAssignmentModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveMultipleQuestionAssignmentRequest extends SaveAssignmentRequest {
    private int questionCount;

    @Override
    public SaveECLassAssignmentModel toModel() {
        var builder = SaveMultipleQuestionAssignmentModel.builder()
            .questionCount(questionCount);
        applyCommonFields(builder);
        return builder.build();
    }
}
