package com.elearning.eclass.validation;

import com.elearning.eclass.request.SaveAssignmentRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@AllArgsConstructor
public class EClassAssignmentValidator {

    public Mono<Map<String, String>> validate(SaveAssignmentRequest rq) {
        return Mono.empty();
    }
}
