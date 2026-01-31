package com.elearning.eclass.pagination;

import com.pagination.mongodb.pagination.DefaultPaginationFilter;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.TextCriteria;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class EClassPaginationFilter implements DefaultPaginationFilter {

    private String key;

    @Override
    public List<CriteriaDefinition> queryOperator() {
        List<CriteriaDefinition> criterias = new ArrayList<>();
        if (key != null) {
            criterias.add(new TextCriteria().matching(key));
        }
        return criterias;
    }
}
