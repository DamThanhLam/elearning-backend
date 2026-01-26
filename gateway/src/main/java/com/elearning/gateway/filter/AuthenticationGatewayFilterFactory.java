package com.elearning.gateway.filter;

import com.auth.idp.util.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationGatewayFilterFactory.class);

    @Autowired
    private JwtTokenProvider tokenProvider;

    public AuthenticationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            HttpCookie bearer = exchange.getRequest().getCookies().getFirst("ACCESS_TOKEN");
            String jwtToken = bearer != null ? bearer.getValue() : "";
            return tokenProvider.validateToken(jwtToken)
                .flatMap(userSession -> {
                    if (!userSession.isActive()) {
                        return Mono.error(new BadCredentialsException("JWT token is invalid"));
                    }
                    builder.headers(
                        httpHeaders ->
                            httpHeaders.set(
                                "AuthenticatedUserId",
                                String.valueOf(userSession.getUserId())
                            )
                    );
                    return chain.filter(exchange.mutate().request(builder.build()).build());
                })
                .onErrorResume(e -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
        };
    }


    public static class Config {
    }
}