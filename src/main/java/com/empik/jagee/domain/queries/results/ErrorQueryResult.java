package com.empik.jagee.domain.queries.results;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorQueryResult implements IQueryResult {

    private String message;
}
