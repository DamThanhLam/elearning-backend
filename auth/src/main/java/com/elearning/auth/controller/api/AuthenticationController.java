package com.elearning.auth.controller.api;

import com.auth.idp.util.JwtTokenProvider;
import com.elearning.auth.request.LoginRequest;
import com.elearning.auth.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> loginPost(
        @RequestBody Mono<LoginRequest> request,
        ServerWebExchange exchange
    ) {
        return request
            .flatMap(credentials ->
                authenticationService.authenticate(
                    credentials.getUsername(),
                    credentials.getPassword()
                )
            )
            .flatMap(customUserDetails ->
                jwtTokenProvider.generateToken(
                    exchange,
                    customUserDetails
                )
            )
            .map(jwt -> {
                exchange.getResponse().addCookie(
                    ResponseCookie.from("ACCESS_TOKEN")
                        .httpOnly(true)
                        .path("/")
                        .value(jwt)
                        .build()
                );
                return ResponseEntity.ok().build();
            })
            .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
