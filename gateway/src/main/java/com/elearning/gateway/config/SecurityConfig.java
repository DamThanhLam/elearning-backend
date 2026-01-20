package com.elearning.gateway.config;

import com.elearning.gateway.filter.EmailVerifiedAfterAuthenticationFilter;
import com.auth.idp.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.preauth.x509.SubjectX500PrincipalExtractor;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean("filterChain")
    @Order(2)
    public SecurityWebFilterChain filterChain(
        ServerHttpSecurity http,
        JwtAuthenticationFilter jwtAuthenticationFilter,
        EmailVerifiedAfterAuthenticationFilter emailVerifiedAfterAuthenticationFilter
    ) {
        SubjectX500PrincipalExtractor principalExtractor =
            new SubjectX500PrincipalExtractor();
        principalExtractor.setExtractPrincipalNameFromEmail(true);

        http
            .x509((x509) -> x509
                .principalExtractor(principalExtractor)
            )
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(ServerHttpSecurity.CorsSpec::disable)
            .authorizeExchange((authorize) -> authorize
                .anyExchange().permitAll()
            )
            .addFilterAt(
                jwtAuthenticationFilter,
                SecurityWebFiltersOrder.AUTHENTICATION
            )
            .addFilterAfter(
                emailVerifiedAfterAuthenticationFilter,
                SecurityWebFiltersOrder.AUTHENTICATION
            );
        return http.build();
    }
}
