package com.elearning.eclass.response;

import com.elearning.elearning_sdk.entity.QuestionId;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class MultipleQuestionsAssignmentResponse extends AssignmentDetailResponse {
    private List<QuestionId> questionIds;
    private int questionCount;
}
