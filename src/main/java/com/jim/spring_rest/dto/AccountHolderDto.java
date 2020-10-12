package com.jim.spring_rest.dto;

import com.jim.spring_rest.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class AccountHolderDto extends BaseEntityDto {
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private AddressDto address;
}
