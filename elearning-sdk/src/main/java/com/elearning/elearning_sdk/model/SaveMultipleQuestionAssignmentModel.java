package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.Assignment;
import com.elearning.elearning_sdk.entity.MultipleQuestionsAssignment;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class SaveMultipleQuestionAssignmentModel extends SaveECLassAssignmentModel {
    private int questionCount;

    @Override
    public Assignment getEntity() {
        MultipleQuestionsAssignment entity = new MultipleQuestionsAssignment();
        entity.setQuestionCount(questionCount);
        return entity;
    }
}
