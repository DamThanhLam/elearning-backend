package com.elearning.elearning_sdk.resolvers;

import com.elearning.elearning_sdk.annotation.AuthenticatedUserId;
import org.bson.types.ObjectId;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class AuthenticatedUserIdResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedUserId.class);
    }

    @Override
    public Mono<Object> resolveArgument(
        MethodParameter parameter,
        BindingContext bindingContext,
        ServerWebExchange exchange
    ) {
        return Mono.just(
            new ObjectId(
                Objects.requireNonNull(
                    exchange.getRequest()
                    .getHeaders()
                    .getFirst("AuthenticatedUserId")
                )
            )
        );
    }
}
