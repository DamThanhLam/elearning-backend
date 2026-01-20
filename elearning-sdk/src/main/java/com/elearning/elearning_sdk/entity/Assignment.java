package com.elearning.elearning_sdk.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "assignments")
@CompoundIndexes({
    @CompoundIndex(
        name = "assignment_start_at_and_due_at",
        def = "{'start_at': -1, 'due_at': 1}"
    )
})
public class Assignment {
    @Id
    private ObjectId id;

    @Indexed
    @Field(name = "class_id")
    private ObjectId classId;

    @Field(name = "display_name")
    private String displayName;

    @Field(name = "short_description")
    private String shortDescription;

    private AssignmentType type;

    @Field(name = "start_at")
    private LocalDateTime startAt;

    @Field(name = "due_at")
    private LocalDateTime dueAt;

    private AssignmentStatus status;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
