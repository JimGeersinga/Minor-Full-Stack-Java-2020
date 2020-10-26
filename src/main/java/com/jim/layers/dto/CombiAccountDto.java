package com.jim.layers.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CombiAccountDto extends BaseEntityDto {
    @NotNull
    private AccountDto accountA;
    @NotNull
    private AccountDto accountB;
}
