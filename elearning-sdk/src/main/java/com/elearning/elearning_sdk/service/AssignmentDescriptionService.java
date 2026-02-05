package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.entity.AssignmentDescription;
import com.elearning.elearning_sdk.repository.AssignmentDescriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AssignmentDescriptionService {

    private final AssignmentDescriptionRepository assignmentDescriptionRepository;

    public Mono<Void> save(
        String assignmentId,
        String content
    ) {
        AssignmentDescription entity = new AssignmentDescription();
        entity.setAssignmentId(assignmentId);
        entity.setDescription(content);
        return assignmentDescriptionRepository.save(entity)
            .then();
    }

    public Mono<String> getContent(String assignmentId) {
        return assignmentDescriptionRepository
            .getDescriptionByAssignmentId(assignmentId)
            .map(AssignmentDescription::getDescription);
    }
}
