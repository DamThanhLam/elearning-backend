package com.elearning.eclass.controller.api;

import com.elearning.eclass.controller.service.AssignmentControllerService;
import com.elearning.eclass.pagination.AssignmentPaginationFilter;
import com.elearning.eclass.request.SaveAssignmentRequest;
import com.elearning.eclass.response.AssignmentResponse;
import com.elearning.eclass.validation.EClassAssignmentValidator;
import com.elearning.elearning_sdk.service.ECLassAssignmentService;
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

    private final EClassAssignmentValidator eclassAssignmentValidator;
    private final ECLassAssignmentService eclassAssignmentService;
    private final AssignmentControllerService assignmentControllerService;

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
}
