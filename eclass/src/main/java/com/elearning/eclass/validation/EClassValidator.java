package com.elearning.eclass.validation;

import com.elearning.eclass.request.SaveEClassRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class EClassValidator {
    public Mono<Map<String, String>> validate(SaveEClassRequest request) {
        return Mono.empty();
    }
}
