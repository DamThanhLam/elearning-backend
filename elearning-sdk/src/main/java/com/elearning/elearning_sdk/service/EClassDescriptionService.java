package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.entity.EClassDescription;
import com.elearning.elearning_sdk.repository.EClassDescriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EClassDescriptionService {

    private final EClassDescriptionRepository eclassDescriptionRepository;

    public Mono<Void> save(
        String eClassId,
        String content
    ) {
        EClassDescription entity = new EClassDescription();
        entity.setEclassId(eClassId);
        entity.setDescription(content);
        return eclassDescriptionRepository.save(entity)
            .then();
    }

    public Mono<String> getContent(String assignmentId) {
        return eclassDescriptionRepository
            .getDescriptionByEclassId(assignmentId)
            .map(EClassDescription::getDescription);
    }
}
