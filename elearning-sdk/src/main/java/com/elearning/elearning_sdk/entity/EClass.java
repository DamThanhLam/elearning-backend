package com.elearning.elearning_sdk.entity;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "classes")
public class EClass {

    @Id
    private String id;

    @Indexed
    @Field(name = "teacher_id")
    private String teacherId;

    @TextIndexed
    @Field(name = "display_name")
    private String displayName;

    @TextIndexed
    @Field(name = "short_description")
    private String shortDescription;

    private BigDecimal originalPrice;

    private BigDecimal price;

    private EClassStatus status;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
