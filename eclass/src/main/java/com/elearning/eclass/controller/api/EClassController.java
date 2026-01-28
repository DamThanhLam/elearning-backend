package com.elearning.eclass.controller.api;

import com.elearning.eclass.converter.RequestToModel;
import com.elearning.eclass.request.SaveEClassRequest;
import com.elearning.eclass.validation.EClassValidator;
import com.elearning.elearning_sdk.annotation.AuthenticatedUserId;
import com.elearning.elearning_sdk.service.EClassService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/eclasses")
@AllArgsConstructor
public class EClassController {

    private final EClassValidator eclassValidator;
    private final EClassService eclassService;
    private final RequestToModel requestToModel;

    @PostMapping
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
}
