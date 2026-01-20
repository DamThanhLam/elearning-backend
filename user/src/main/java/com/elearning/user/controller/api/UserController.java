package com.elearning.user.controller.api;

import com.elearning.elearning_sdk.annotation.AuthenticatedUserId;
import com.elearning.elearning_sdk.controller.service.EmailService;
import com.elearning.elearning_sdk.controller.service.OneTimeTokenService;
import com.elearning.elearning_sdk.model.GenerateOneTimeTokenModel;
import com.elearning.elearning_sdk.service.UserService;
import com.elearning.user.converter.RequestToModel;
import com.elearning.user.request.ChangePasswordRequest;
import com.elearning.user.request.ForgotPasswordRequest;
import com.elearning.user.request.SaveUserInformationRequest;
import com.elearning.user.request.SaveUserRequest;
import com.elearning.user.validation.UserValidator;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.elearning.user.constant.Constant.OTP_SUBJECT;
import static com.elearning.user.constant.Constant.OTP_VERIFY_TEMPLATE;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final OneTimeTokenService oneTimeTokenService;
    private final UserValidator userValidator;
    private final UserService userService;
    private final RequestToModel requestToModel;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

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

    @PutMapping("/users/profiles")
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
                            .then(Mono.just(ResponseEntity.ok().build()))
                ))
        );
    }

    @PostMapping("/users/change-password")
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
                                userService.updatePassword(
                                    userId,
                                    passwordEncode
                                ))
                            .then(Mono.just(ResponseEntity.ok().build()));
                    })
                )
        );
    }

    @PostMapping("/users/forgot-password/send-otp")
    public Mono<ResponseEntity<Object>> forgotPassword(
        @RequestBody Mono<ForgotPasswordRequest> request
    ) {
        return request
            .flatMap(rq ->
                oneTimeTokenService.generate(
                    new GenerateOneTimeTokenModel(
                        rq.getEmail()
                    )
                )
            )
            .flatMap(ott ->
                emailService
                    .sendMail(
                        ott.getUsername(),
                        OTP_SUBJECT,
                        OTP_VERIFY_TEMPLATE.replace("<otp>", ott.getTokenValue())
                    )
                    .then(Mono.just(ott))
            )
            .then(Mono.defer(() ->
                Mono.just(ResponseEntity.noContent().build())
            ));
    }
}
