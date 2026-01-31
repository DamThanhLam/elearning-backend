package com.pagination.mongodb.service;

import com.pagination.mongodb.model.PaginationModel;
import com.pagination.mongodb.pagination.DefaultPaginationFilter;
import com.pagination.mongodb.pagination.Pageable;
import com.pagination.mongodb.repository.DefaultPaginationRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public abstract class AbstractPaginationService<M, R, F extends DefaultPaginationFilter>
    implements DefaultPaginationService<M, R, F> {

    private final DefaultPaginationRepository<R, F> repository;

    @Override
    public Mono<PaginationModel<M>> getNextPagination(
        F filter,
        String nextPageToken,
        Pageable pageable
    ) {
        return repository.getNextPagination(filter, nextPageToken, pageable)
            .map(items -> items.map(this::converterToModel));
    }

    @Override
    public Mono<PaginationModel<M>> getPreviousPagination(
        F filter,
        String previousPageToken,
        Pageable pageable
    ) {
        return repository.getPreviousPagination(filter, previousPageToken, pageable)
            .map(items -> items.map(this::converterToModel));
    }

    protected abstract M converterToModel(R r);
}
