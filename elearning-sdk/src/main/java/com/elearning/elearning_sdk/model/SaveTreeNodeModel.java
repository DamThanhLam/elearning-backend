package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.NodeStatus;
import com.elearning.elearning_sdk.entity.NodeType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class SaveTreeNodeModel {
    private String name;
    private NodeType type;
    private NodeStatus status;
}
