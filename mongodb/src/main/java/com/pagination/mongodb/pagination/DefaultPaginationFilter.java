package com.pagination.mongodb.pagination;

import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.util.List;

public interface DefaultPaginationFilter {
    List<CriteriaDefinition> queryOperator();
}
