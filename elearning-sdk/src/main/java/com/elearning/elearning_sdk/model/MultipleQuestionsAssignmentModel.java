package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.QuestionId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class MultipleQuestionsAssignmentModel extends AssignmentModel {
    private List<QuestionId> questionIds;
    private int questionCount;
}
