package com.elearning.eclass.service;

import com.elearning.eclass.pagination.EClassPaginationFilter;
import com.elearning.eclass.repository.EClassPaginationRepository;
import com.elearning.elearning_sdk.converter.EntityToModel;
import com.elearning.elearning_sdk.entity.EClass;
import com.elearning.elearning_sdk.model.EClassModel;
import com.pagination.mongodb.service.AbstractPaginationService;
import org.springframework.stereotype.Service;

@Service
public class EClassPaginationService
    extends AbstractPaginationService<EClassModel, EClass, EClassPaginationFilter> {

    private final EntityToModel entityToModel;

    public EClassPaginationService(
        EClassPaginationRepository repository,
        EntityToModel entityToModel
    ) {
        super(repository);
        this.entityToModel = entityToModel;
    }

    @Override
    protected EClassModel converterToModel(EClass entity) {
        return entityToModel.toModel(entity);
    }
}
