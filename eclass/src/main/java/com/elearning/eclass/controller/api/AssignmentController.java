package com.elearning.eclass.controller.api;

import com.elearning.eclass.controller.service.AssignmentControllerService;
import com.elearning.eclass.pagination.AssignmentPaginationFilter;
import com.elearning.eclass.request.SaveAssignmentRequest;
import com.elearning.eclass.response.AssignmentResponse;
import com.elearning.eclass.validation.AssignmentValidator;
import com.elearning.elearning_sdk.annotation.AuthenticatedUserId;
import com.elearning.elearning_sdk.service.AssignmentDescriptionService;
import com.elearning.elearning_sdk.service.AssignmentService;
import com.pagination.mongodb.model.PaginationModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AssignmentController {

    private final AssignmentValidator assignmentValidator;
    private final AssignmentService eclassAssignmentService;
    private final AssignmentControllerService assignmentControllerService;
    private final AssignmentService assignmentService;
    private final AssignmentDescriptionService assignmentDescriptionService;

    @PostMapping("/teachers/me/eclasses/{id}/assignments")
    public Mono<ResponseEntity<Object>> assignmentPost(
        @PathVariable String id,
        @RequestBody Mono<SaveAssignmentRequest> request
    ) {
        return request.flatMap(rq ->
            assignmentValidator.validate(rq)
                .map(errors -> ResponseEntity.badRequest().body((Object) errors))
                .switchIfEmpty(
                    eclassAssignmentService
                        .addAssignment(id, rq.toModel())
                        .map(ResponseEntity::ok)
                )
        );
    }

    @GetMapping("/teachers/me/eclasses/{id}/assignments")
    public Mono<ResponseEntity<PaginationModel<AssignmentResponse>>> assignmentsGet(
        @PathVariable String id,
        @RequestParam(required = false) String nextPageToken,
        @RequestParam(required = false) String previousPageToken,
        @PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return assignmentControllerService
            .getAssignmentPagination(
                AssignmentPaginationFilter.builder()
                    .eclassId(id)
                    .build(),
                nextPageToken,
                previousPageToken,
                pageable
            )
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.ok().build());
    }

    @PutMapping("/teachers/me/eclasses/{eclassId}/assignments/{assignmentId}")
    public Mono<ResponseEntity<Object>> assignmentPut(
        @AuthenticatedUserId String userId,
        @PathVariable String eclassId,
        @PathVariable String assignmentId,
        @RequestBody Mono<SaveAssignmentRequest> request
    ) {
        return request.flatMap(rq ->
            assignmentValidator.validate(userId, eclassId, assignmentId, rq)
                .map(errors -> ResponseEntity.badRequest().body((Object) errors))
                .switchIfEmpty(
                    eclassAssignmentService
                        .updateAssignment(assignmentId, rq.toModel())
                        .thenReturn(ResponseEntity.ok().build())
                )
        );
    }

    @GetMapping("/teachers/me/eclasses/{eclassId}/assignments/{assignmentId}")
    public Mono<ResponseEntity<Object>> getAssignmentDetail(
        @AuthenticatedUserId String userId,
        @PathVariable String eclassId,
        @PathVariable String assignmentId
    ) {
        return assignmentValidator.validate(userId, eclassId, assignmentId)
            .then(
                assignmentControllerService.getAssignmentDetail(assignmentId)
                    .map(result -> ResponseEntity.ok((Object) result))
            )
            .onErrorResume(errors ->
                Mono.just(ResponseEntity.badRequest().body(errors.getMessage()))
            );
    }

    @GetMapping("/teachers/me/eclasses/{eclassId}/assignments/{assignmentId}/description")
    public Mono<ResponseEntity<String>> getAssignmentDescription(
        @AuthenticatedUserId String userId,
        @PathVariable String eclassId,
        @PathVariable String assignmentId
    ) {
        return assignmentValidator.validate(userId, eclassId, assignmentId)
            .then(
                assignmentDescriptionService.getContent(assignmentId)
                    .map(ResponseEntity::ok)
            )
            .onErrorResume(errors ->
                Mono.just(ResponseEntity.badRequest().body(errors.getMessage()))
            );
    }
}
