package com.elearning.elearning_sdk.entity;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "tree_nodes")
public class TreeNode {
    @Id
    private String id;

    @Field(name = "user_id")
    private String userId;

    private String name;

    private List<String> children;

    private NodeType type;

    private NodeStatus status;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
