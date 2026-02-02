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

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class MappingMediaService {

    private final MappingMediaRepository mappingMediaRepository;
    private final ModelToEntity modelToEntity;
    private final EntityToModel entityToModel;

    public Mono<String> addMappingMedia(
        String classId,
        SaveMappingMediaModel model
    ) {
        MappingMedia entity = modelToEntity.toEntity(model);
        entity.setId(classId);
        return mappingMediaRepository.save(entity)
            .map(MappingMedia::getId);
    }

    public Mono<Void> updateMappingMedia(
        String id,
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

    public Flux<String> getMediaIdByEntityId(String entityId) {
        return mappingMediaRepository.getMediaIdByEntityId(entityId);
    }

    public Mono<String> getFirstMediaIdByEntityIdAndType(
        String entityId,
        MappingMediaType type
    ) {
        return mappingMediaRepository.getFirstMediaIdByEntityIdAndType(
            entityId,
            type
        );
    }

    public Flux<String> getMediaIdByEntityIdAndType(
        String entityId,
        MappingMediaType type
    ) {
        return mappingMediaRepository.getMediaIdByEntityIdAndType(
            entityId,
            type
        );
    }

    public Mono<Map<String, MappingMediaModel>> getMediaMapByEntityIds(List<String> entityIds) {
        return mappingMediaRepository.getMediaIdByEntityIdIn(entityIds)
            .map(entityToModel::toModel)
            .collectMap(
                MappingMediaModel::getEntityId,
                Function.identity()
            );
    }

    public Mono<MappingMediaModel> getMappingMediaById(String id) {
        return mappingMediaRepository.findById(id)
            .map(entityToModel::toModel);
    }

    public Mono<MappingMediaModel> getMappingMediaByMappingMediaType(MappingMediaType type) {
        return mappingMediaRepository.findByType(type)
            .map(entityToModel::toModel);
    }

    private Mono<MappingMedia> getMappingMediaByIdOrThrow(
        String id
    ) {
        return mappingMediaRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("MappingMedia")));
    }
}
