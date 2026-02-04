package com.elearning.eclass.converter;

import com.elearning.eclass.request.SaveAssignmentRequest;
import com.elearning.eclass.request.SaveEClassRequest;
import com.elearning.eclass.request.SaveMultipleQuestionAssignmentRequest;
import com.elearning.eclass.request.SaveProgrammingAssignmentRequest;
import com.elearning.elearning_sdk.model.SaveECLassAssignmentModel;
import com.elearning.elearning_sdk.model.SaveEClassModel;
import com.elearning.elearning_sdk.model.SaveMultipleQuestionAssignmentModel;
import com.elearning.elearning_sdk.model.SaveProgrammingAssignmentModel;
import org.springframework.stereotype.Component;

@Component
public class RequestToModel {

    public SaveEClassModel toModel(SaveEClassRequest request) {
        return SaveEClassModel.builder()
            .displayName(request.getDisplayName())
            .shortDescription(request.getShortDescription())
            .description(request.getDescription())
            .status(request.getStatus())
            .build();
    }

    public SaveECLassAssignmentModel toModel(SaveAssignmentRequest request) {
        if (request instanceof SaveProgrammingAssignmentRequest saveProgrammingAssignmentRequest) {
            return SaveProgrammingAssignmentModel.builder()
                .languageVersion(saveProgrammingAssignmentRequest.getLanguageVersion())
                .language(saveProgrammingAssignmentRequest.getLanguage())
                .type(request.getType())
                .dueAt(request.getDueAt())
                .startAt(request.getStartAt())
                .displayName(request.getDisplayName())
                .shortDescription(request.getShortDescription())
                .status(request.getStatus())
                .build();
        } else if (request instanceof SaveMultipleQuestionAssignmentRequest saveMultipleQuestionAssignmentRequest) {
            return SaveMultipleQuestionAssignmentModel.builder()
                .questionCount(saveMultipleQuestionAssignmentRequest.getQuestionCount())
                .type(request.getType())
                .dueAt(request.getDueAt())
                .startAt(request.getStartAt())
                .displayName(request.getDisplayName())
                .shortDescription(request.getShortDescription())
                .status(request.getStatus())
                .build();
        }
        throw new IllegalArgumentException(
            "Unsupported assignment type: " + request.getClass()
        );
    }
}
