package com.elearning.eclass.controller.service;

import com.elearning.eclass.converter.ModelToResponse;
import com.elearning.eclass.pagination.AssignmentPaginationFilter;
import com.elearning.eclass.response.AssignmentResponse;
import com.elearning.eclass.service.AssignmentPaginationService;
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
}
