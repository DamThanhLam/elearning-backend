package com.pagination.mongodb.pagination;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Builder
@Getter
public class Pageable {
    private int size;
    private Sort sort;
}
