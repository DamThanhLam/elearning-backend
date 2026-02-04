package com.elearning.eclass.request;

import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.entity.AssignmentType;
import com.elearning.elearning_sdk.model.SaveECLassAssignmentModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SaveProgrammingAssignmentRequest.class, name = "PROGRAMMING"),
    @JsonSubTypes.Type(value = SaveMultipleQuestionAssignmentRequest.class, name = "MULTIPLE_QUESTIONS")
})
@Setter
@Getter
public abstract class SaveAssignmentRequest {
    private String displayName;
    private String description;
    private String shortDescription;
    private AssignmentType type;
    private long startAt;
    private long dueAt;
    private AssignmentStatus status;

    protected void applyCommonFields(
        SaveECLassAssignmentModel.SaveECLassAssignmentModelBuilder<?, ?> builder
    ) {
        builder
            .type(getType())
            .dueAt(getDueAt())
            .startAt(getStartAt())
            .displayName(getDisplayName())
            .description(getDescription())
            .shortDescription(getShortDescription())
            .status(getStatus());
    }

    public SaveECLassAssignmentModel toModel() {
        return null;
    }
}
