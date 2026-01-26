package com.elearning.auth.service;

import com.auth.idp.converter.AuthEntityToModel;
import com.elearning.elearning_sdk.model.UserInformationModel;
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
    private final AuthEntityToModel authEntityToModel;

    public Mono<UserInformationModel> authenticate(
        String email,
        String password
    ) {
        return userRepository.findByEmail(email)
            .filter(user ->
                passwordEncoder.matches(password, user.getPassword())
            )
            .map(authEntityToModel::toUserInformationModel);
    }
}
