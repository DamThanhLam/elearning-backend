package com.elearning.eclass.controller.decorator;

import com.elearning.eclass.converter.ModelToResponse;
import com.elearning.eclass.response.ECLassResponse;
import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.entity.MappingMediaType;
import com.elearning.elearning_sdk.model.EClassModel;
import com.elearning.elearning_sdk.service.AssignmentService;
import com.elearning.elearning_sdk.service.ECLassMemberService;
import com.elearning.elearning_sdk.service.MappingMediaService;
import com.elearning.elearning_sdk.service.MediaService;
import com.pagination.mongodb.model.PaginationModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.elearning.elearning_sdk.constant.Constant.DEFAULT_AVATAR;

@Component
@AllArgsConstructor
public class EClassDecorator {

    private final ModelToResponse modelToResponse;
    private final AssignmentService AssignmentService;
    private final ECLassMemberService eclassMemberService;
    private final MappingMediaService mappingMediaService;
    private final MediaService mediaService;

    public Mono<PaginationModel<ECLassResponse>> decorateToEClassResponse(
        PaginationModel<EClassModel> pagination
    ) {
        return pagination.mapAsync(this::decorateToEClassResponse);
    }

    public Mono<ECLassResponse> decorateToEClassResponse(
        EClassModel model
    ) {
        String eclassId = model.getId();
        Mono<Long> numberMembers = eclassMemberService.countEClassMember(eclassId);
        Mono<Long> numberAssignments = AssignmentService.countAssignmentByEClassIdByStatus(
            eclassId,
            AssignmentStatus.OPEN
        );
        return mappingMediaService
            .getFirstMediaIdByEntityIdAndType(
                model.getId(),
                MappingMediaType.AVATAR
            )
            .flatMap(mediaService::getMedia)
            .defaultIfEmpty(DEFAULT_AVATAR)
            .flatMap(avatar ->
                Mono.zip(numberMembers, numberAssignments)
                    .map(mapper ->
                        modelToResponse.toResponse(
                            model,
                            avatar,
                            mapper.getT1(),
                            mapper.getT2()
                        )
                    )
            );
    }
}
