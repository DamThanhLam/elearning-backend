package com.auth.idp.filter;

import com.auth.idp.service.CustomReactiveUserDetailsService;
import com.auth.idp.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@NullMarked
@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Mono<Authentication> authMono =
            getJwtFromRequest(exchange)
                .flatMap(tokenProvider::validateToken)
                .flatMap(userSession ->
                    userDetailsService.findByUsername(
                        tokenProvider.getEmailFromJWT(userSession.getToken())
                    )
                )
                .map(userDetails ->
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    )
                );

        return authMono
            .flatMap(auth ->
                chain.filter(exchange)
                    .contextWrite(
                        ReactiveSecurityContextHolder.withAuthentication(auth)
                    )
            )
            .switchIfEmpty(chain.filter(exchange));
    }

    public Mono<String> getJwtFromRequest(ServerWebExchange exchange) {
        return Mono.justOrEmpty(
                exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION)
            )
            .filter(auth -> auth.startsWith("Bearer "))
            .map(auth -> auth.substring(7));
    }
}
