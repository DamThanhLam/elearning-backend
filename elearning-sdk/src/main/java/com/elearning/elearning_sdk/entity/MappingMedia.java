package com.elearning.elearning_sdk.entity;

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
@Document(collection = "mapping_medias")
@CompoundIndexes({
    @CompoundIndex(
        name = "mapping_media_media_id_and_entity_id_and_secondary_key_and_type",
        def = "{'media_id': 1, 'entity_id': 1, 'secondary_key': 1, 'type': 1}"
    )
})
public class MappingMedia {
    @Id
    private ObjectId id;

    @Field(name = "media_id")
    private ObjectId mediaId;

    @Field(name = "entity_id")
    private ObjectId entityId;

    private MappingMediaType type;

    @CreatedDate
    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private LocalDateTime updatedAt;
}
