package com.jim.security.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Role {

    @Id
    private Long Id;

    private String name;
}