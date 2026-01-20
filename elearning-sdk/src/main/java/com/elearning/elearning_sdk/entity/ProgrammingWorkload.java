package com.elearning.elearning_sdk.entity;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "programming_workloads")
public class ProgrammingWorkload {
    @Id
    private ObjectId id;

    @Field(name = "user_id")
    private ObjectId userId;

    @Field(name = "assignment_id")
    private ObjectId assignmentId;

    private List<TreeNode> nodes;

    @Field(name = "run_configuration_id")
    private ObjectId runConfigurationId;
}
