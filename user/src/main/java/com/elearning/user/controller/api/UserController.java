package com.elearning.user.controller.api;

import com.elearning.elearning_sdk.annotation.AuthenticatedUserId;
import com.elearning.elearning_sdk.controller.service.OneTimeTokenService;
import com.elearning.elearning_sdk.event.SendEmailEvent;
import com.elearning.elearning_sdk.model.GenerateOneTimeTokenModel;
import com.elearning.elearning_sdk.model.OneTimeTokenAuthenticationTokenModel;
import com.elearning.elearning_sdk.service.UserService;
import com.elearning.user.converter.RequestToModel;
import com.elearning.user.request.*;
import com.elearning.user.validation.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.elearning.user.constant.Constant.OTP_SUBJECT;
import static com.elearning.user.constant.Constant.OTP_VERIFY_TEMPLATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final OneTimeTokenService oneTimeTokenService;
    private final UserValidator userValidator;
    private final UserService userService;
    private final RequestToModel requestToModel;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/sign-up")
    public Mono<ResponseEntity<Object>> signUpPost(
        @RequestBody Mono<SaveUserRequest> request
    ) {
        return request.flatMap(req ->
            userValidator.validateSaveUserRequest(req)
                .map(errors -> ResponseEntity.badRequest().body((Object) errors))
                .switchIfEmpty(
                    userService.addUser(
                        requestToModel.toModel(
                            req,
                            Objects.requireNonNull(passwordEncoder.encode(req.getPassword())))
                        )
                        .map(userId -> ResponseEntity.ok().body(userId))
                )
        );
    }

    @PutMapping("/profiles")
    public Mono<ResponseEntity<Object>> userProfilesPut(
        @AuthenticatedUserId Mono<ObjectId> authenticatedUserId,
        @RequestBody Mono<SaveUserInformationRequest> request
    ) {
        return request.flatMap(req ->
            userValidator.validate(req)
                .map(errors -> ResponseEntity.ok().body((Object) errors))
                .switchIfEmpty(
                    Mono.defer(() ->
                        authenticatedUserId
                            .flatMap(userId ->
                                userService.updateUser(
                                    userId,
                                    requestToModel.toModel(
                                        req
                                    )
                            ))
                            .thenReturn(ResponseEntity.ok().build())
                ))
        );
    }

    @PostMapping("/change-password")
    public Mono<ResponseEntity<Object>> changePassword(
        @AuthenticatedUserId Mono<ObjectId> authenticatedUserId,
        @RequestBody Mono<ChangePasswordRequest> request
    ) {
        return request.flatMap(req ->
            userValidator.validate(req)
                .map(errors -> ResponseEntity.ok().body((Object) errors))
                .switchIfEmpty(
                    Mono.defer(() -> {
                        String passwordEncode = passwordEncoder.encode(req.getNewPassword());
                        return authenticatedUserId
                            .flatMap(userId ->
                                userService.updatePasswordById(
                                    userId,
                                    passwordEncode
                                ))
                            .thenReturn(ResponseEntity.ok().build());
                    })
                )
        );
    }

    @PostMapping("/forgot-password/send-otp")
    public Mono<ResponseEntity<Object>> forgotPasswordSendOtp(
        @RequestBody Mono<ForgotPasswordRequest> request
    ) {
        return request
            .flatMap(rq ->
                userValidator.validate(rq)
                    .map(e -> ResponseEntity.badRequest().body((Object)e))
                    .switchIfEmpty(Mono.defer(() ->
                        oneTimeTokenService.generate(
                            new GenerateOneTimeTokenModel(
                                rq.getEmail()
                            )
                        ).doOnNext(ott ->
                            eventPublisher.publishEvent(
                                SendEmailEvent.builder()
                                    .toEmailUser( ott.getUsername())
                                    .subject(OTP_SUBJECT)
                                    .content(OTP_VERIFY_TEMPLATE.replace("<otp>", ott.getTokenValue()))
                                    .build()
                            )
                        ).thenReturn(ResponseEntity.ok().build())
                    ))
            );
    }

    @PostMapping("/forgot-password/otp/verify")
    public Mono<ResponseEntity<Object>> forgotPasswordOtpVerify(
        @RequestBody Mono<VerifyForgotPasswordRequest> request
    ) {
        return request
            .flatMap( rq ->
                userValidator.validate(rq)
                    .filter(errors -> !errors.isEmpty())
                    .map(errors -> ResponseEntity.badRequest().body((Object)errors))
                    .switchIfEmpty(Mono.defer(() ->
                        oneTimeTokenService
                            .consume(
                                new OneTimeTokenAuthenticationTokenModel(
                                    rq.getEmail(),
                                    rq.getOtp()
                                )
                            )
                            .flatMap(token ->
                                userService
                                    .updatePasswordByEmail(
                                        token.getUsername(),
                                        passwordEncoder.encode(
                                            rq.getNewPassword()
                                        )
                                    )
                                    .thenReturn(ResponseEntity.ok().build())
                            )
                            .switchIfEmpty(
                                Mono.defer(() ->Mono.just(ResponseEntity.badRequest().build()))
                            )
                    ))
            )
            .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping("/information")
    public Mono<ResponseEntity<Object>> userInformation(
        @AuthenticatedUserId ObjectId userId
    ) {
        return userService.getUserInformationById(userId)
            .map(ResponseEntity::ok);
    }
}
