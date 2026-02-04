package com.elearning.eclass.repository;

import com.elearning.eclass.pagination.AssignmentPaginationFilter;
import com.elearning.elearning_sdk.entity.Assignment;
import com.pagination.mongodb.repository.AbstractPaginationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AssignmentPaginationRepository
    extends AbstractPaginationRepository<Assignment, AssignmentPaginationFilter> {
}
