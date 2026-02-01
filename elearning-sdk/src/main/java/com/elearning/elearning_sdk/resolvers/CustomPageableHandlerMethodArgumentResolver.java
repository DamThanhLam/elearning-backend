package com.elearning.elearning_sdk.resolvers;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public class CustomPageableHandlerMethodArgumentResolver
    implements HandlerMethodArgumentResolver {

    private static final String PAGE_PARAM = "page";
    private static final String SIZE_PARAM = "size";
    private static final String SORT_PARAM = "sort";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Mono<Object> resolveArgument(
        MethodParameter parameter,
        BindingContext bindingContext,
        ServerWebExchange exchange
    ) {
        var params = exchange.getRequest().getQueryParams();

        PageableDefault pageableDefault =
            parameter.getParameterAnnotation(PageableDefault.class);

        int defaultPage = pageableDefault != null ? pageableDefault.page() : 0;
        int defaultSize = pageableDefault != null ? pageableDefault.size() : 20;
        Sort defaultSort = pageableDefault != null
            ? Sort.by(pageableDefault.direction(), pageableDefault.sort())
            : Sort.unsorted();

        int page = Optional.ofNullable(params.getFirst(PAGE_PARAM))
            .map(Integer::parseInt)
            .orElse(defaultPage);

        int size = Optional.ofNullable(params.getFirst(SIZE_PARAM))
            .map(Integer::parseInt)
            .orElse(defaultSize);

        Sort sort = resolveSort(params.get(SORT_PARAM), defaultSort);

        Pageable pageable = PageRequest.of(page, size, sort);

        return Mono.just(pageable);
    }

    private Sort resolveSort(List<String> sortParams, Sort defaultSort) {
        if (sortParams == null || sortParams.isEmpty()) {
            return defaultSort;
        }

        return Sort.by(
            sortParams.stream()
                .map(param -> {
                    String[] parts = param.split(",");
                    return parts.length == 2
                        ? new Sort.Order(
                        Sort.Direction.fromString(parts[1]),
                        parts[0]
                    )
                        : new Sort.Order(Sort.Direction.ASC, parts[0]);
                })
                .toList()
        );
    }
}
