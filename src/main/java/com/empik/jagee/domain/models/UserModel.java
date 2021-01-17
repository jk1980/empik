package com.empik.jagee.domain.models;

import lombok.Builder;

@Builder
public class UserModel {

    private String id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private String createdAt;
    private Integer followers;
    private Integer publicRepos;
    private Integer requestCount;
}
