package com.elearning.eclass.controller.api;

import com.elearning.eclass.converter.RequestToModel;
import com.elearning.eclass.request.SaveAssignmentRequest;
import com.elearning.eclass.validation.EClassAssignmentValidator;
import com.elearning.elearning_sdk.service.ECLassAssignmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class EClassAssignmentController {

    private final EClassAssignmentValidator eclassAssignmentValidator;
    private final ECLassAssignmentService eclassAssignmentService;
    private final RequestToModel requestToModel;

    @PostMapping("/teachers/me/eclasses/{id}/assignments")
    public Mono<ResponseEntity<Object>> teacherClassAssignment(
        @PathVariable String id,
        @RequestBody Mono<SaveAssignmentRequest> request
    ) {
        return request.flatMap(rq ->
            eclassAssignmentValidator.validate(rq)
                .map(errors -> ResponseEntity.badRequest().body((Object) errors))
                .switchIfEmpty(
                    eclassAssignmentService
                        .addAssignment(id, rq.toModel())
                        .map(ResponseEntity::ok)
                )
        );
    }
}
