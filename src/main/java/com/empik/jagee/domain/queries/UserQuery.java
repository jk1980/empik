package com.empik.jagee.domain.queries;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserQuery implements IQuery {
    private String login;
}
