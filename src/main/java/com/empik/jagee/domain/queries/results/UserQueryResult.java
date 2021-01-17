package com.empik.jagee.domain.queries.results;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class UserQueryResult implements IQueryResult {

    private String id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private String createdAt;
    private BigDecimal calculations;
}
