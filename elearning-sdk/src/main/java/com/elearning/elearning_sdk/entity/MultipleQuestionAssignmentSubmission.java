package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@TypeAlias("multiple_question_assignment_submission")
public class MultipleQuestionAssignmentSubmission extends AssignmentSubmission {
    @Field(name = "question_answer_ids")
    private List<String> questionAnswerIds;
}
