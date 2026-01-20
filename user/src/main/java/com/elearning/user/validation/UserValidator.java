package com.elearning.user.validation;

import com.elearning.elearning_sdk.service.UserService;
import com.elearning.user.request.ChangePasswordRequest;
import com.elearning.user.request.SaveUserInformationRequest;
import com.elearning.user.request.SaveUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class UserValidator {

    private final UserService userService;

    public Mono<Map<String, String>> validateSaveUserRequest(
        SaveUserRequest request
    ) {
        Map<String, String> errors = new HashMap<>();

        if (request.getDisplayName() == null || request.getDisplayName().isBlank()) {
            errors.put("displayName", "Display name is required");
        }

        if (request.getPassword() == null || request.getPassword().length() < 6) {
            errors.put("password", "Password must be at least 6 characters");
        }

        if (request.getRole() == null) {
            errors.put("role", "Role is required");
        }

        String email = request.getEmail();
        if (email == null || email.isBlank()) {
            errors.put("email", "Email is required");
        }

        return userService.getUserInformationByEmail(email)
            .map(user -> {
                errors.put("email", "Email already exists");
                return errors;
            })
            .switchIfEmpty(Mono.defer(() ->
                errors.isEmpty() ? Mono.empty() : Mono.just(errors)
            ));
    }

    public Mono<Map<String, String>> validate(SaveUserInformationRequest request) {
        Map<String, String> errors = new HashMap<>();
        if (request.getDisplayName() == null || request.getDisplayName().isBlank()) {
            errors.put("displayName", "Display name is required");
        }
        if (request.getPhone() == null || request.getPhone().isBlank()) {
            errors.put("phone", "Phone is required");
        }
        return Mono.just(errors);
    }

    public Mono<Map<String, String>> validate(ChangePasswordRequest request) {
        return Mono.empty();
    }
}
