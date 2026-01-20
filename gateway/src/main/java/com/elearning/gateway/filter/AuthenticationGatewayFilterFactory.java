package com.elearning.gateway.filter;

import com.auth.idp.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    @Autowired
    private JwtTokenProvider tokenProvider;

    public AuthenticationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            HttpCookie bearer = exchange.getRequest().getCookies().getFirst("Authorization");
            String jwtToken = bearer != null ? bearer.getValue().replace("Bearer ", "") : "";

            return tokenProvider.validateToken(jwtToken)
                .flatMap(userSession -> {
                    if (userSession.isActive()) {
                        builder.headers(
                            httpHeaders ->
                                httpHeaders.set(
                                    "AuthenticatedUserId",
                                    String.valueOf(userSession.getUserId())
                                )
                        );
                        return chain.filter(exchange.mutate().request(builder.build()).build());
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                });
        };
    }


    public static class Config {
    }
}