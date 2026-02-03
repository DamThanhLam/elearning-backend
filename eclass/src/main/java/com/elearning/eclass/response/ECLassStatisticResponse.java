package com.elearning.eclass.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ECLassStatisticResponse {
    private long totalAssignments;
    private long openAssignments;
    private long upcomingAssignments;
}
