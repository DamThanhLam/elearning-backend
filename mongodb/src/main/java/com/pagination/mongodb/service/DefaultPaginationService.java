package com.pagination.mongodb.service;

import com.pagination.mongodb.model.PaginationModel;
import com.pagination.mongodb.pagination.DefaultPaginationFilter;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface DefaultPaginationService<M, R, F extends DefaultPaginationFilter> {

    Mono<PaginationModel<M>> getNextPagination(
        F filter,
        String nextPageToken,
        Pageable pageable
    );

    Mono<PaginationModel<M>> getPreviousPagination(
        F filter,
        String previousPageToken,
        Pageable pageable
    );
}
