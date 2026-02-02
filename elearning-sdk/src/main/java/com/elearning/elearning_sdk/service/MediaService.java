package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.converter.ModelToEntity;
import com.elearning.elearning_sdk.entity.Media;
import com.elearning.elearning_sdk.exception.NotFoundException;
import com.elearning.elearning_sdk.model.MediaModel;
import com.elearning.elearning_sdk.model.SaveMediaModel;
import com.elearning.elearning_sdk.repository.MediaRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final ModelToEntity modelToEntity;
    private final EntityToModel entityToModel;

    public Mono<String> addMedia(SaveMediaModel model) {
        Media entity = modelToEntity.toEntity(model);
        return mediaRepository.save(entity)
            .map(Media::getId);
    }

    public Mono<Void> updateMedia(String id, SaveMediaModel model) {
        return getMediaByIdOrThrow(id)
            .map(entity -> {
                modelToEntity.mergeToEntity(model, entity);
                return entity;
            })
            .flatMap(mediaRepository::save)
            .then();
    }

    public Mono<MediaModel> getMedia(String id) {
        return mediaRepository.findById(id)
            .map(entityToModel::toModel);
    }

    private Mono<Media> getMediaByIdOrThrow(String id) {
        return mediaRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("media")));
    }
}
