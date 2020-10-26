package com.jim.layers.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class AccountDto extends BaseEntityDto {
    @ApiModelProperty(readOnly = true)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String iban;

    @Min(value = 0)
    private Double amount;
    private Boolean isBlocked;
}
