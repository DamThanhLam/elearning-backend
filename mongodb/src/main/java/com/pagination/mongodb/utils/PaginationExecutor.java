package com.pagination.mongodb.utils;

import com.pagination.mongodb.model.PaginationModel;
import com.pagination.mongodb.pagination.DefaultPaginationFilter;
import com.pagination.mongodb.pagination.Pageable;
import com.pagination.mongodb.service.DefaultPaginationService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PaginationExecutor {

    public static <M, R, F extends DefaultPaginationFilter>
    Mono<PaginationModel<M>> getPagination(
        DefaultPaginationService<M, R, F> service,
        F filter,
        String nextPageToken,
        String prevPageToken,
        Pageable pageable
    ) {
        return prevPageToken != null
            ? service.getPreviousPagination(filter, prevPageToken, pageable)
            : service.getNextPagination(filter, nextPageToken, pageable);
    }
}
