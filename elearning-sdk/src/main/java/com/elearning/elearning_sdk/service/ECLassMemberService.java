package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.entity.EClassMemberStatus;
import com.elearning.elearning_sdk.repository.EClassMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ECLassMemberService {

    private final EClassMemberRepository eclassMemberRepository;

    public Mono<Long> countEClassMember(
        String eclassId
    ) {
        return eclassMemberRepository.countByClassIdAndStatus(
            eclassId,
            EClassMemberStatus.ACTIVATED
        );
    }
}
