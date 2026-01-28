package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.converter.ModelToEntity;
import com.elearning.elearning_sdk.entity.EClass;
import com.elearning.elearning_sdk.exception.NotFoundException;
import com.elearning.elearning_sdk.model.EClassModel;
import com.elearning.elearning_sdk.model.SaveEClassModel;
import com.elearning.elearning_sdk.repository.EClassRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class EClassService {

    private final EClassRepository eclassRepository;
    private final ModelToEntity modelToEntity;
    private final EntityToModel entityToModel;

    public Mono<ObjectId> addEClass(
        ObjectId teacherId,
        SaveEClassModel model
    ) {
        EClass entity = modelToEntity.toEntity(model);
        entity.setTeacherId(teacherId);
        return eclassRepository.save(entity)
            .map(EClass::getId);
    }

    public Mono<Void> updateEClass(
        ObjectId id,
        SaveEClassModel model
    ) {
        return getEClassByIdOrThrow(id)
            .map(entity -> {
                modelToEntity.mergeToEntity(model, entity);
                return entity;
            })
            .map(eclassRepository::save)
            .then();
    }

    public Mono<EClassModel> getEClassById(ObjectId id) {
        return eclassRepository.findById(id)
            .map(entityToModel::toModel);
    }

    public Flux<EClassModel> getEClassByIds(List<ObjectId> ids) {
        return eclassRepository.findAllById(ids)
            .map(entityToModel::toModel);
    }

    public Flux<EClassModel> getEClassesByTeacherId(
        ObjectId teacherId
    ) {
        return eclassRepository.findByTeacherId(teacherId)
            .map(entityToModel::toModel);
    }

    private Mono<EClass> getEClassByIdOrThrow(ObjectId id) {
        return eclassRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("eclass")));
    }
}
