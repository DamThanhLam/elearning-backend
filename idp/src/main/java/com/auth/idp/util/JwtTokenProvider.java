package com.auth.idp.util;

import com.auth.idp.entity.CustomUserDetails;
import com.auth.idp.entity.UserSessionType;
import com.auth.idp.model.UserSessionModel;
import com.auth.idp.service.UserSessionService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
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
        CustomUserDetails userDetails
    ) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        String jwtToken = Jwts.builder()
            .subject(userDetails.getUser().getEmail())
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
        UserSessionModel userSessionModel = buildUserSessionModel(
            exchange,
            userDetails,
            jwtToken,
            toLocalDateTime(now),
            toLocalDateTime(expiryDate)
        );
        return userSessionService.addUserSession(userSessionModel)
            .thenReturn(jwtToken);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
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
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return Mono.empty();
    }

    private UserSessionModel buildUserSessionModel(
        ServerWebExchange exchange,
        CustomUserDetails userDetails,
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
            .userId(userDetails.getUser().getId())
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
