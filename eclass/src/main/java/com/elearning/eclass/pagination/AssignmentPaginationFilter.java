package com.elearning.eclass.pagination;

import com.pagination.mongodb.pagination.DefaultPaginationFilter;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class AssignmentPaginationFilter implements DefaultPaginationFilter {

    private String eclassId;

    @Override
    public List<CriteriaDefinition> queryOperator() {
        List<CriteriaDefinition> criteria = new ArrayList<>();
        if (eclassId != null) {
            criteria.add(
                new Criteria("eclassId").is(eclassId)
            );
        }
        return criteria;
    }
}
