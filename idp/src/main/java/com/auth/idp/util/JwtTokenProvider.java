package com.auth.idp.util;

import com.auth.idp.entity.UserSessionType;
import com.auth.idp.model.UserSessionModel;
import com.auth.idp.service.UserSessionService;
import com.elearning.elearning_sdk.model.UserInformationModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;

import static com.auth.idp.util.ClockProxy.toLocalDateTime;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION;

    @Autowired
    private UserSessionService userSessionService;

    public Mono<String> generateToken(
        ServerWebExchange exchange,
        UserInformationModel userInformationModel
    ) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        String jwtToken = Jwts.builder()
            .subject(userInformationModel.getEmail())
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSigningKey())
            .compact();
        UserSessionModel userSessionModel = buildUserSessionModel(
            exchange,
            userInformationModel,
            jwtToken,
            toLocalDateTime(now),
            toLocalDateTime(expiryDate)
        );
        return userSessionService.addUserSession(userSessionModel)
            .thenReturn(jwtToken);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return claims.getSubject();
    }

    public Mono<UserSessionModel> validateToken(String authToken) {
        try {
            if (!authToken.isEmpty()) {
                Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);
                return userSessionService.getUserSessionByToken(
                    authToken
                );
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.error(e);
        }
        return Mono.error(new BadCredentialsException("JWT token is invalid"));
    }

    private UserSessionModel buildUserSessionModel(
        ServerWebExchange exchange,
        UserInformationModel userInformationModel,
        String jwtToken,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt
    ) {
        ServerHttpRequest request = exchange.getRequest();
        String userAgent = request.getHeaders().getFirst("User-Agent");
        String ipAddress = null;
        if (request.getHeaders().containsHeader("X-Forwarded-For")) {
            ipAddress = request.getHeaders().getFirst("X-Forwarded-For");
        } else if (request.getRemoteAddress() != null) {
            ipAddress = request.getRemoteAddress().getAddress().getHostAddress();
        }
        LocalDateTime now = LocalDateTime.now();
        return UserSessionModel.builder()
            .userId(userInformationModel.getId())
            .token(jwtToken)
            .ipAddress(ipAddress)
            .userAgent(userAgent)
            .active(true)
            .type(UserSessionType.ACCESS_TOKEN)
            .issuedAt(issuedAt)
            .expiredAt(expiredAt)
            .lastAccessAt(now)
            .build();
    }
}
