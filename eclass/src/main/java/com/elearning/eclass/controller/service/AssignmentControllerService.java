package com.elearning.eclass.controller.service;

import com.elearning.eclass.converter.ModelToResponse;
import com.elearning.eclass.pagination.AssignmentPaginationFilter;
import com.elearning.eclass.response.AssignmentDetailResponse;
import com.elearning.eclass.response.AssignmentResponse;
import com.elearning.eclass.response.MultipleQuestionsAssignmentResponse;
import com.elearning.eclass.response.ProgrammingAssignmentResponse;
import com.elearning.eclass.service.AssignmentPaginationService;
import com.elearning.elearning_sdk.model.MultipleQuestionsAssignmentModel;
import com.elearning.elearning_sdk.model.ProgrammingAssignmentModel;
import com.elearning.elearning_sdk.service.AssignmentService;
import com.pagination.mongodb.model.PaginationModel;
import com.pagination.mongodb.utils.PaginationExecutor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AssignmentControllerService {

    private final AssignmentPaginationService assignmentPaginationService;
    private final ModelToResponse modelToResponse;
    private final AssignmentService assignmentService;

    public Mono<PaginationModel<AssignmentResponse>> getAssignmentPagination(
        AssignmentPaginationFilter filter,
        String nextPageToken,
        String previousPageToken,
        Pageable pageable
    ) {
        return PaginationExecutor
            .getPagination(
                assignmentPaginationService,
                filter,
                nextPageToken,
                previousPageToken,
                pageable
            )
            .map(pagination ->
                pagination.map(modelToResponse::toResponse)
            );
    }

    public Mono<AssignmentDetailResponse> getAssignmentDetail(
        String assignmentId
    ) {
        return assignmentService.getAssignmentById(assignmentId)
            .flatMap(model -> {
                if (model instanceof MultipleQuestionsAssignmentModel subModel) {
                    return Mono.just(modelToResponse.toResponse(subModel));
                }
                if (model instanceof ProgrammingAssignmentModel subModel) {
                    return Mono.just(modelToResponse.toResponse(subModel));
                }
                return Mono.empty();
            });
    }
}
