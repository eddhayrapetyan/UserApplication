package com.jambit.testdocker.dto;

import com.jambit.testdocker.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PersonDto {
    private Long id;

    private String name;

    private Integer age;

    private Date birthDate;

    private Gender gender;

    private String username;

    private String password;

    private String faculty;

    private Set<String> universities = new HashSet<>();
}
