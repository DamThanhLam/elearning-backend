package com.pagination.mongodb.repository;

import com.pagination.mongodb.model.PaginationModel;
import com.pagination.mongodb.pagination.DefaultPaginationFilter;
import com.pagination.mongodb.pagination.Pageable;
import reactor.core.publisher.Mono;

public interface DefaultPaginationRepository<R, F extends DefaultPaginationFilter> {

    Mono<PaginationModel<R>> getNextPagination(
        F filter,
        String nextPageToken,
        Pageable pageable
    );

    Mono<PaginationModel<R>> getPreviousPagination(
        F filter,
        String previousPageToken,
        Pageable pageable
    );
}
