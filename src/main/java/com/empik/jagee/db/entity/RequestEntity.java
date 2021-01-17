package com.empik.jagee.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class RequestEntity {

    @Id
    @Column(name = "LOGIN")
    private String login;

    @Column(name = "REQUEST_COUNT")
    private Integer requestCount;
}
