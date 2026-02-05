package com.elearning.eclass.controller.service;

import com.elearning.eclass.controller.decorator.EClassDecorator;
import com.elearning.eclass.converter.ModelToResponse;
import com.elearning.eclass.pagination.EClassPaginationFilter;
import com.elearning.eclass.response.ECLassResponse;
import com.elearning.eclass.response.ECLassStatisticResponse;
import com.elearning.eclass.service.EClassPaginationService;
import com.elearning.elearning_sdk.entity.AssignmentStatus;
import com.elearning.elearning_sdk.service.AssignmentService;
import com.elearning.elearning_sdk.service.EClassService;
import com.pagination.mongodb.model.PaginationModel;
import com.pagination.mongodb.utils.PaginationExecutor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.elearning.eclass.constant.Constant.UPCOMING_ASSIGNMENT_DAYS;

@Component
@AllArgsConstructor
public class EClassControllerService {

    private final EClassService eclassService;
    private final EClassPaginationService eclassPaginationService;
    private final EClassDecorator eclassDecorator;
    private final AssignmentService AssignmentService;
    private final ModelToResponse modelToResponse;

    public Mono<PaginationModel<ECLassResponse>> getEClassPagination(
        EClassPaginationFilter filter,
        String nextPageToken,
        String prevPageToken,
        Pageable pageable
    ) {
        return PaginationExecutor
            .getPagination(
                eclassPaginationService,
                filter,
                nextPageToken,
                prevPageToken,
                pageable
            )
            .flatMap(eclassDecorator::decorateToEClassResponse);
    }

    public Mono<ECLassResponse> getEClassTeacher(String id) {
        return eclassService.getEClassById(id)
            .flatMap(eclassDecorator::decorateToEClassResponse);
    }

    public Mono<ECLassStatisticResponse> getEClassStatistic(
        String id
    ) {
        Mono<Long> totalAssignments = AssignmentService
            .countTotalAssignmentByEClassId(id);
        Mono<Long> openAssignments = AssignmentService
            .countAssignmentByEClassIdByStatus(id, AssignmentStatus.OPEN);
        Mono<Long> upcomingAssignments = AssignmentService.countUpcomingAssignments(
            id,
            UPCOMING_ASSIGNMENT_DAYS
        );
        return Mono.zip(totalAssignments, openAssignments, upcomingAssignments)
            .map(mapper ->
                modelToResponse.toECLassStatisticResponse(
                    mapper.getT1(),
                    mapper.getT2(),
                    mapper.getT3()
                )
            );
    }
}
