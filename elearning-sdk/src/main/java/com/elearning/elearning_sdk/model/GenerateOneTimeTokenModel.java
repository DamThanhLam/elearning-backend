package com.elearning.elearning_sdk.model;

import java.time.Duration;

public class GenerateOneTimeTokenModel {
    private static final Duration DEFAULT_EXPIRES_IN = Duration.ofMinutes(5L);
    private final String username;
    private final Duration expiresIn;

    public GenerateOneTimeTokenModel(
        String username
    ){
        this(username, DEFAULT_EXPIRES_IN);
    }

    public GenerateOneTimeTokenModel(
        String username,
        Duration expiresIn
    ) {
        this.username = username;
        this.expiresIn = expiresIn;
    }

    public String getUsername() {
        return this.username;
    }

    public Duration getExpiresIn() {
        return this.expiresIn;
    }
}
