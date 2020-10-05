package com.jim.spring_rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private int id;
    private String accountNumber;
    private Double amount;
    private Boolean isBlocked;

}
