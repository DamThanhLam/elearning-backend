package com.elearning.elearning_sdk.entity;

import com.elearning.elearning_sdk.model.SaveRedisModel;
import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class DefaultOneTimeToken extends SaveRedisModel implements OneTimeToken{

    private final String token;
    private final String username;
    private final Instant expireAt;

    public String getTokenValue() {
        return this.token;
    }

    public String getUsername() {
        return this.username;
    }

    public Instant getExpiresAt() {
        return this.expireAt;
    }
}
