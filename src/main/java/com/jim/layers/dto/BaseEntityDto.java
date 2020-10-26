package com.jim.layers.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseEntityDto {
    @ApiModelProperty(readOnly = true)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @ApiModelProperty(readOnly = true)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime created;

    @ApiModelProperty(readOnly = true)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime lastModified;
}