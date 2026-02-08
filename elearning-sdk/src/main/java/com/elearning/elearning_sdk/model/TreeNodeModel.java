package com.elearning.elearning_sdk.model;

import com.elearning.elearning_sdk.entity.NodeStatus;
import com.elearning.elearning_sdk.entity.NodeType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Getter
public class TreeNodeModel {
    private String id;
    private String name;
    private List<String> children;
    private NodeType type;
    private NodeStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
