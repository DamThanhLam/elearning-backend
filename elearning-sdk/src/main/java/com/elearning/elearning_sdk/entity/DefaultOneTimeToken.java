package com.elearning.elearning_sdk.entity;

import com.elearning.elearning_sdk.model.SaveRedisModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class DefaultOneTimeToken extends SaveRedisModel implements OneTimeToken{

    private final String tokenValue;
    private final String username;
    private final Instant expiresAt;

    @JsonCreator
    public DefaultOneTimeToken(
        @JsonProperty("tokenValue") String tokenValue,
        @JsonProperty("username") String username,
        @JsonProperty("expiresAt") Instant expiresAt
    ) {
        this.tokenValue = tokenValue;
        this.username = username;
        this.expiresAt = expiresAt;
    }

    public String getTokenValue() {
        return this.tokenValue;
    }

    public String getUsername() {
        return this.username;
    }

    public Instant getExpiresAt() {
        return this.expiresAt;
    }
}
