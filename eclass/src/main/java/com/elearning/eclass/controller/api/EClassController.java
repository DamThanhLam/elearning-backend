package com.elearning.eclass.controller.api;

import com.elearning.eclass.controller.service.EClassControllerService;
import com.elearning.eclass.converter.RequestToModel;
import com.elearning.eclass.pagination.EClassPaginationFilter;
import com.elearning.eclass.request.SaveEClassRequest;
import com.elearning.eclass.response.ECLassTeacherResponse;
import com.elearning.eclass.validation.EClassValidator;
import com.elearning.elearning_sdk.annotation.AuthenticatedUserId;
import com.elearning.elearning_sdk.service.EClassService;
import com.pagination.mongodb.model.PaginationModel;
import com.pagination.mongodb.pagination.Pageable;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class EClassController {

    private final EClassValidator eclassValidator;
    private final RequestToModel requestToModel;
    private final EClassControllerService eclassControllerService;
    private final EClassService eclassService;

    @PostMapping("/eclasses")
    public Mono<ResponseEntity<Object>> eclassPost(
        @AuthenticatedUserId ObjectId userId,
        @RequestBody Mono<SaveEClassRequest> request
    ) {
        return request.flatMap(rq ->
            eclassValidator.validate(rq)
                .map(errors -> ResponseEntity.badRequest().body((Object) errors))
                .switchIfEmpty(Mono.defer(() ->
                    eclassService
                        .addEClass(
                            userId,
                            requestToModel.toModel(rq)
                        )
                        .thenReturn(ResponseEntity.ok().build())
                ))
        );
    }

    @GetMapping("/teachers/me/eclasses")
    public Mono<ResponseEntity<PaginationModel<ECLassTeacherResponse>>> eclassGet(
        @RequestParam(required = false) String nextPageToken,
        @RequestParam(required = false) String previousPageToken,
        @RequestParam(required = false) String displayName,
        @PageableDefault(size = 15, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return eclassControllerService
            .getEClassTeacherPagination(
                EClassPaginationFilter.builder().key(displayName).build(),
                nextPageToken,
                previousPageToken,
                pageable
            )
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.ok().build());
    }
}
