package com.elearning.gateway.filter;

import com.elearning.elearning_sdk.service.UserService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.elearning.elearning_sdk.constant.Constant.HEADER_NAME_AUTHENTICATED_USER_ID;

@Component
@AllArgsConstructor
public class EmailVerifiedAfterAuthenticationFilter implements WebFilter {

    private final UserService userService;

    @Override
    public Mono<Void> filter(
        ServerWebExchange exchange,
        WebFilterChain chain
    ) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().contextPath().value();
        String userId = request.getHeaders().getFirst(HEADER_NAME_AUTHENTICATED_USER_ID);
        if ("/verify-otp".equals(path) || userId == null) {
            return chain.filter(exchange);
        }
        return userService
            .getUserById(
                new ObjectId(userId)
            )
            .flatMap(user -> {
                if (user.isEmailVerified()) {
                    return chain.filter(exchange);
                }

                exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                exchange.getResponse().getHeaders()
                    .setLocation(URI.create("/verify-otp?email=" + user.getEmail()));
                return exchange.getResponse().setComplete();
            });
    }
}
