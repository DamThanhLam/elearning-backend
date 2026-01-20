package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.converter.ModelToEntity;
import com.elearning.elearning_sdk.entity.MappingMedia;
import com.elearning.elearning_sdk.entity.MappingMediaType;
import com.elearning.elearning_sdk.exception.NotFoundException;
import com.elearning.elearning_sdk.model.MappingMediaModel;
import com.elearning.elearning_sdk.model.SaveMappingMediaModel;
import com.elearning.elearning_sdk.repository.MappingMediaRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MappingMediaService {

    private final MappingMediaRepository mappingMediaRepository;
    private final ModelToEntity modelToEntity;
    private final EntityToModel entityToModel;

    public Mono<ObjectId> addMappingMedia(
        ObjectId classId,
        String secondaryKey,
        SaveMappingMediaModel model
    ) {
        MappingMedia entity = modelToEntity.toEntity(model);
        entity.setId(classId);
        entity.setSecondaryKey(secondaryKey);
        return mappingMediaRepository.save(entity)
            .map(MappingMedia::getId);
    }

    public Mono<Void> updateMappingMedia(
        ObjectId id,
        SaveMappingMediaModel model
    ) {
        return getMappingMediaByIdOrThrow(id)
            .map(entity -> {
                modelToEntity.mergeToEntity(model, entity);
                return entity;
            })
                .flatMap(mappingMediaRepository::save)
                .then();
    }

    public Flux<ObjectId> getMediaIdByEntityId(ObjectId entityId) {
        return mappingMediaRepository.getMediaIdByEntityId(entityId);
    }

    public Mono<MappingMediaModel> getMappingMediaById(ObjectId id) {
        return mappingMediaRepository.findById(id)
            .map(entityToModel::toModel);
    }

    public Mono<MappingMediaModel> getMappingMediaByMappingMediaType(MappingMediaType type) {
        return mappingMediaRepository.findByType(type)
            .map(entityToModel::toModel);
    }

    private Mono<MappingMedia> getMappingMediaByIdOrThrow(
        ObjectId id
    ) {
        return mappingMediaRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("MappingMedia")));
    }
}
