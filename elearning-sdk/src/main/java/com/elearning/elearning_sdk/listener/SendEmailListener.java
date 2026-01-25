package com.elearning.elearning_sdk.listener;

import com.elearning.elearning_sdk.controller.service.EmailService;
import com.elearning.elearning_sdk.event.SendEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailListener {
    private final EmailService emailService;

    @Async
    @EventListener
    public void handle(
        SendEmailEvent event
    ) {
        emailService
            .sendMail(
                event.getToEmailUser(),
                event.getSubject(),
                event.getContent()
            )
            .subscribe();
    }
}
