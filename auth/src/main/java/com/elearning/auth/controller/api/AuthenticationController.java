package com.elearning.auth.controller.api;

import com.auth.idp.util.JwtTokenProvider;
import com.elearning.auth.converter.ModelToResponse;
import com.elearning.auth.request.LoginRequest;
import com.elearning.auth.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelToResponse modelToResponse;

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> loginPost(
        @RequestBody Mono<LoginRequest> request,
        ServerWebExchange exchange
    ) {
        return request
            .flatMap(credentials -> {
                log.info("loginPost username={}", credentials.getPassword());
                return authenticationService.authenticate(
                    credentials.getUsername(),
                    credentials.getPassword()
                );
            })
            .flatMap(userInformationModel -> {
                log.info("loginPost displayName={}", userInformationModel.getDisplayName());
                return jwtTokenProvider
                    .generateToken(
                        exchange,
                        userInformationModel
                    )
                    .map(jwt -> {
                        exchange.getResponse().addCookie(
                            ResponseCookie.from("ACCESS_TOKEN")
                                .httpOnly(true)
                                .path("/")
                                .value(jwt)
                                .build()
                        );
                        return userInformationModel;
                    });
            })
            .map(modelToResponse::toResponse)
            .map(ResponseEntity::ok);
    }
}
