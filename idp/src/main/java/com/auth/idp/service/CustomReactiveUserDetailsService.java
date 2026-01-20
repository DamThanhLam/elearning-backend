package com.auth.idp.service;

import com.auth.idp.entity.CustomUserDetails;
import com.elearning.elearning_sdk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@NullMarked
public class CustomReactiveUserDetailsService
    implements ReactiveUserDetailsService,
        ReactiveUserDetailsPasswordService {

    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
            .map(CustomUserDetails::new);
    }

    @Override
    public Mono<UserDetails> updatePassword(
        UserDetails user,
        @Nullable String newPassword
    ) {
        return userRepository.findByEmail(user.getUsername())
            .flatMap(u -> {
                u.setPassword(newPassword);
                return userRepository.save(u);
            })
            .map(CustomUserDetails::new);
    }
}
