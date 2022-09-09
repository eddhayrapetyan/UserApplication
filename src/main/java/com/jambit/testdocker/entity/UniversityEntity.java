package com.jambit.testdocker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "university")
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

//    @OneToOne
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToMany(mappedBy = "universities")
    private Set<PersonEntity> persons = new HashSet<>();

    public void setPersons(Set<PersonEntity> persons) {
        this.persons = persons;
    }

    public void addPerson(PersonEntity person) {
        this.persons.add(person);
    }

    public void removePerson(PersonEntity person) {
        this.persons.remove(person);
    }

    public Set<PersonEntity> getPersons() {
        return persons;
    }

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            },
//            mappedBy = "universities")
//    @JsonIgnore
//    private Set<PersonEntity> persons;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
