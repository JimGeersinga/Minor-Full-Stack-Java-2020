package com.jim.layers.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
public class Account extends BaseEntity {
    @NotNull
    @Column(length = 8)
    @Size(min = 8, max = 8)
    private String accountNumber;

    @NotNull
    private String iban;

    @NotNull
    @Min(value = 0)
    private Double amount;

    @NotNull
    private Boolean isBlocked;
}
