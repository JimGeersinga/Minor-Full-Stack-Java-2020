package com.jim.spring_rest.dto;

import com.jim.spring_rest.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountHolderDto extends BaseEntityDto {
    private String name;
    private Gender gender;
    private Date dateOfBirth;
    private AddressDto address;
}
