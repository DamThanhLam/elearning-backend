package com.elearning.elearning_sdk.controller.service;

import com.elearning.elearning_sdk.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class EmailService {

    private final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String mailFrom;

    private final JavaMailSender mailSender;
    private final UserService userService;

    public EmailService (
        JavaMailSender mailSender,
        UserService userService
    ) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public Mono<Void> sendMail(
        String toEmailUser,
        String subject,
        String content
    ) {
        return Mono
            .fromCallable(() -> {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(mailFrom);
                message.setTo(toEmailUser);
                message.setSubject(subject);
                message.setText(content);
                log.info("DOING sendMail(...)");
                mailSender.send(message);
                log.info("END sendMail(...)");
                return true;
            })
            .doOnCancel(() -> log.info("SMTP SEND CANCEL"))
            .subscribeOn(Schedulers.boundedElastic())
            .then();
    }

    public Mono<Void> sendMail(
        ObjectId toUserId,
        String subject,
        String content
    ) {
        return userService
            .getEmailByUserId(toUserId)
                .flatMap(email ->
                    sendMail(email, subject, content)
                )
            .then();
    }
}
