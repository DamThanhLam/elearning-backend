package com.elearning.elearning_sdk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OneTimeTokenAuthenticationTokenModel {
    private final String principal;
    private final String tokenValue;
}
