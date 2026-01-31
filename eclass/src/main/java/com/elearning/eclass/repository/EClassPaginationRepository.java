package com.elearning.eclass.repository;

import com.elearning.eclass.pagination.EClassPaginationFilter;
import com.elearning.elearning_sdk.entity.EClass;
import com.pagination.mongodb.repository.AbstractPaginationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EClassPaginationRepository
    extends AbstractPaginationRepository<EClass, EClassPaginationFilter> {
}
