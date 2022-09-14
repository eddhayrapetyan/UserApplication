package com.jambit.testdocker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UniversityDto {
    private long id;

    private String name;

    private AddressDto addressDto;

    private Set<String> persons = new HashSet<>();
}
