package com.elearning.eclass.validation;

import com.elearning.eclass.request.SaveAssignmentRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@AllArgsConstructor
public class AssignmentValidator {

    public Mono<Map<String, String>> validate(SaveAssignmentRequest rq) {
        return Mono.empty();
    }

    public Mono<Map<String, String>> validate(
        String userId,
        String eclassId,
        String assignmentId,
        SaveAssignmentRequest rq
    ) {
        return Mono.empty();
    }

    public Mono<Void> validate(
        String userId,
        String eclassId,
        String assignmentId
    ) {
        return Mono.empty();
    }
}
