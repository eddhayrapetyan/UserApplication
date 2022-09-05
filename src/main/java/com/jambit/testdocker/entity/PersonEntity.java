package com.jambit.testdocker.entity;

import com.jambit.testdocker.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Transient
    private Integer age;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String username;

    private String password;

    private String faculty;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "uniPersonEntity_id")
    Set<UniPersonEntity> uniPersonEntitySet;

}
