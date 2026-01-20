package com.elearning.elearning_sdk.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

@Getter
@Setter
@TypeAlias("programming_assigment")
public class ProgrammingAssignment extends Assignment {
    private ProgrammingLanguage language;

    private String version;
}
