package com.elearning.auth.service;

import com.auth.idp.entity.CustomUserDetails;
import com.elearning.elearning_sdk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<CustomUserDetails> authenticate(
        String email,
        String password
    ) {
        return userRepository.findByEmail(email)
            .filter(user -> {
                if (passwordEncoder.matches(password, user.getPassword())) {
                    return true;
                }
                return false;
            })
            .switchIfEmpty(Mono.error(new RuntimeException("Invalid email or password")))
            .map(CustomUserDetails::new);
    }
}
