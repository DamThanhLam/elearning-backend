package com.elearning.eclass.service;

import com.elearning.eclass.pagination.AssignmentPaginationFilter;
import com.elearning.eclass.repository.AssignmentPaginationRepository;
import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.entity.Assignment;
import com.elearning.elearning_sdk.model.AssignmentModel;
import com.pagination.mongodb.service.AbstractPaginationService;
import org.springframework.stereotype.Service;

@Service
public class AssignmentPaginationService
    extends AbstractPaginationService<AssignmentModel, Assignment, AssignmentPaginationFilter> {

    private final EntityToModel entityToModel;

    public AssignmentPaginationService(
        AssignmentPaginationRepository repository,
        EntityToModel entityToModel
    ) {
        super(repository);
        this.entityToModel = entityToModel;
    }

    @Override
    protected AssignmentModel converterToModel(Assignment entity) {
        return entityToModel.toModel(entity);
    }
}
