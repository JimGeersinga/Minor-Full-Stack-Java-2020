package com.jim.layers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombiAccountDto extends BaseEntityDto {
    private AccountDto accountA;
    private AccountDto accountB;
}
