package com.jim.layers.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Account extends BaseEntity {
    private String accountNumber;
    private String iban;
    private Double amount;
    private Boolean isBlocked;
}
