package com.elearning.eclass.controller.service;

import com.elearning.eclass.controller.decorator.EClassDecorator;
import com.elearning.eclass.pagination.EClassPaginationFilter;
import com.elearning.eclass.response.ECLassTeacherResponse;
import com.elearning.eclass.service.EClassPaginationService;
import com.pagination.mongodb.model.PaginationModel;
import com.pagination.mongodb.pagination.Pageable;
import com.pagination.mongodb.utils.PaginationExecutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class EClassControllerService {

    private final EClassPaginationService eclassPaginationService;
    private final EClassDecorator eclassDecorator;

    public Mono<PaginationModel<ECLassTeacherResponse>> getEClassTeacherPagination(
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
            .flatMap(eclassDecorator::decorateToEClassTeacherResponse);
    }
}
