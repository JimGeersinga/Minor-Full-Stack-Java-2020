package com.jim.spring_rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto extends BaseEntityDto{
    private String accountNumber;
    private Double amount;
    private Boolean isBlocked;

}
