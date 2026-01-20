package com.elearning.elearning_sdk.entity;

import java.time.Instant;

public interface OneTimeToken {
    String getTokenValue();

    String getUsername();

    Instant getExpiresAt();
}
