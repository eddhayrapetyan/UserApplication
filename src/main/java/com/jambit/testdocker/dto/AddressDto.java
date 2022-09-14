package com.jambit.testdocker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private long id;

    private String city;

    private String street;

    private String building;

}
