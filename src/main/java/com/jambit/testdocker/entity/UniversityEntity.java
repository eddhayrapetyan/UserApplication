package com.jambit.testdocker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "university")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniversityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "uniPersonEntity_id")
    Set<UniPersonEntity> uniPersonEntitySet;

    @OneToOne()
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    AddressEntity address;
}
