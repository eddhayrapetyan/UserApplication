package com.jambit.testdocker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "uniPersonEntity_id")
//    Set<UniPersonEntity> uniPersonEntitySet;

    @Column(name = "university_name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "address_id")
    AddressEntity address;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "universities")
    @JsonIgnore
    private Set<PersonEntity> persons;

}
