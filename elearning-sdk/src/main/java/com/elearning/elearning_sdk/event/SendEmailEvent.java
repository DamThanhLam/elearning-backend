package com.elearning.elearning_sdk.event;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendEmailEvent {
    private String toEmailUser;
    private String subject;
    private String content;
}
