package com.empik.jagee.rest.dtos;

import lombok.Data;

@Data
public class UserDto {

    private String id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private String createdAt;
    private String calculations;
}