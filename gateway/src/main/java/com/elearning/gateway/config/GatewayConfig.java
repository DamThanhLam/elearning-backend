package com.elearning.gateway.config;

import com.elearning.gateway.filter.AuthenticationGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public AuthenticationGatewayFilterFactory authenticationGatewayFilterFactory() {
        return new AuthenticationGatewayFilterFactory();
    }
}
