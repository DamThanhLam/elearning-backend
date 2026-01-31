package com.pagination.mongodb.repository;

import com.pagination.mongodb.model.PaginationModel;
import com.pagination.mongodb.pagination.DefaultPaginationFilter;
import com.pagination.mongodb.pagination.Pageable;
import com.pagination.mongodb.pagination.PaginationTokenCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public abstract class AbstractPaginationRepository<R, F extends DefaultPaginationFilter>
    implements DefaultPaginationRepository<R, F> {

    @Autowired
    private ReactiveMongoTemplate template;

    @Autowired
    private PaginationTokenCodec tokenCodec;

    private Class<R> resultType;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Mono<PaginationModel<R>> getNextPagination(
        F filter,
        String nextPageToken,
        Pageable pageable
    ) {
        Assert.notNull(nextPageToken, "nextPageToken must not be null");
        return doPagination(filter, nextPageToken, pageable, true);
    }

    @Override
    public Mono<PaginationModel<R>> getPreviousPagination(
        F filter,
        String previousPageToken,
        Pageable pageable
    ) {
        Assert.notNull(previousPageToken, "previousPageToken must not be null");
        return doPagination(filter, previousPageToken, pageable, false);
    }

    private Mono<PaginationModel<R>> doPagination(
        F filter,
        String pageToken,
        Pageable pageable,
        boolean isNextPage
    ) {
        List<Sort.Order> orders = pageable.getSort().get().toList();
        Query query = buildQuery(filter, pageToken, pageable, orders, isNextPage);
        return template.find(query, resultType)
            .collectList()
            .map(list -> buildPaginationModel(list, orders));
    }

    private Query buildQuery(
        F filter,
        String pageToken,
        Pageable pageable,
        List<Sort.Order> orders,
        boolean isNextPage
    ) {
        Query query = new Query();

        for (CriteriaDefinition criteria : filter.queryOperator()) {
            query.addCriteria(criteria);
        }
        return query
            .addCriteria(generateCriteriaPageToken(pageToken, orders, isNextPage))
            .limit(pageable.getSize())
            .with(pageable.getSort().reverse());
    }

    private PaginationModel<R> buildPaginationModel(
        List<R> list,
        List<Sort.Order> orders
    ) {
        if (list.isEmpty()) {
            return PaginationModel.<R>builder().build();
        }
        R firstItem = list.get(0);
        R lastItem = list.get(list.size() - 1);
        String previousToken = getPageToken(firstItem, orders);
        String nextToken = getPageToken(lastItem, orders);
        return PaginationModel.<R>builder()
            .items(list)
            .previousPageToken(previousToken)
            .nextPageToken(nextToken)
            .hasPrevious(!previousToken.isEmpty())
            .hasNext(!nextToken.isEmpty())
            .build();
    }


    private String getPageToken(R result, List<Sort.Order> orders) {
        Map<String, Object> keyToken = new HashMap<>();
        for (Sort.Order order : orders) {
            String property = order.getProperty();
            try {
                Method getter = resultType.getMethod(
                    "get" + StringUtils.capitalize(property)
                );
                keyToken.put(property, getter.invoke(result));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.error("Cannot get value for property: {}", property, e);
            }
        }
        return tokenCodec.encode(keyToken);
    }

    private Criteria generateCriteriaPageToken(
        String pageToken,
        List<Sort.Order> orders,
        boolean isNextPage
    ) {
        Map<String, Object> keyToken = tokenCodec.decode(pageToken);
        Criteria criteria = new Criteria();
        for (Sort.Order order : orders) {
            String property = order.getProperty();
            Object value = keyToken.get(property);
            criteria = criteria.and(property);
            if (isNextPage && order.isDescending()) {
                criteria = criteria.lte(value);
            } else {
                criteria = criteria.gte(value);
            }
        }
        return criteria;
    }
}
