package com.ck.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dudycoco on 17-5-4.
 */
@Data
@Table(name = "user")
public class User {
    @Id
    private Long id;
    private String name;
}
