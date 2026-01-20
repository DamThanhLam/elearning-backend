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
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "class_members")
@CompoundIndexes({
    @CompoundIndex(
        name = "eclass_member_class_id_and_user_id_and_status",
        def = "{'class_id': 1, 'user_id': 1, 'status': 1}"
    )
})
public class EClassMember {
    @Id
    private ObjectId id;

    @Field(name = "class_id")
    private ObjectId classId;

    @Field(name = "user_id")
    private ObjectId userId;

    private EClassMemberStatus status;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
