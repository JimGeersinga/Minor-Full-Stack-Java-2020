package com.jim.layers.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account extends BaseEntity {
    private String accountNumber;
    private String iban;
    private Double amount;
    private Boolean isBlocked;
}
