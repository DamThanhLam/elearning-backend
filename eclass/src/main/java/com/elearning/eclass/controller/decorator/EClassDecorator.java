package com.elearning.eclass.controller.decorator;

import com.elearning.eclass.converter.ModelToResponse;
import com.elearning.eclass.response.ECLassTeacherResponse;
import com.elearning.elearning_sdk.entity.MappingMediaType;
import com.elearning.elearning_sdk.model.EClassModel;
import com.elearning.elearning_sdk.service.MappingMediaService;
import com.elearning.elearning_sdk.service.MediaService;
import com.pagination.mongodb.model.PaginationModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class EClassDecorator {

    private final ModelToResponse modelToResponse;
    private final MappingMediaService mappingMediaService;
    private final MediaService mediaService;

    public Mono<PaginationModel<ECLassTeacherResponse>> decorateToEClassTeacherResponse(
        PaginationModel<EClassModel> pagination
    ) {
        return pagination.mapAsync(eclassModel ->
            mappingMediaService
                .getFirstMediaIdByEntityIdAndType(
                    eclassModel.getId(),
                    MappingMediaType.AVATAR
                )
                .flatMap(mediaService::getMedia)
                .map(avatar ->
                    modelToResponse.toModel(eclassModel, avatar)
                )
        );
    }
}
