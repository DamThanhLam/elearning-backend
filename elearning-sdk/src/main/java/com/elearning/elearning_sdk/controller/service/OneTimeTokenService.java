package com.elearning.elearning_sdk.controller.service;

import com.elearning.elearning_sdk.entity.DefaultOneTimeToken;
import com.elearning.elearning_sdk.entity.OneTimeToken;
import com.elearning.elearning_sdk.model.GenerateOneTimeTokenModel;
import com.elearning.elearning_sdk.model.OneTimeTokenAuthenticationTokenModel;
import com.elearning.elearning_sdk.model.SaveRedisModel;
import com.elearning.elearning_sdk.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;

@Service
public class OneTimeTokenService {
    private final Clock clock = Clock.systemUTC();
    private final SecureRandom random = new SecureRandom();

    @Autowired
    private RedisService redisService;

    public Mono<OneTimeToken> generate(GenerateOneTimeTokenModel model) {
        String otp = String.valueOf(100000 + random.nextInt(900000));
        Instant expiresAt = this.clock.instant().plus(model.getExpiresIn());
        SaveRedisModel ott = new DefaultOneTimeToken(otp, model.getUsername(), expiresAt);
        return redisService.save(otp, ott)
            .then(Mono.defer(() -> {
                return Mono.just((OneTimeToken)ott);
            }));
    }

    public Mono<OneTimeToken> consume(OneTimeTokenAuthenticationTokenModel model) {
        String otp = model.getTokenValue();
        return redisService.loadHash(otp)
            .flatMap(value -> {
                if (value instanceof DefaultOneTimeToken ott) {
                    if (
                        !isExpired(ott)
                        && otp.equals(ott.getTokenValue())
                        && model.getPrincipal().equals(ott.getUsername())
                    ) {
                        return redisService.removeHash(otp)
                            .then(Mono.just((OneTimeToken)ott));
                    }
                }
                return Mono.empty();
            });
    }

    private boolean isExpired(OneTimeToken ott) {
        return this.clock.instant().isAfter(ott.getExpiresAt());
    }
}
