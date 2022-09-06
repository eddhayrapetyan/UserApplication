package com.jambit.testdocker.service;

import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.exception.PersonAlreadyExistException;
import com.jambit.testdocker.exception.PersonNotFoundException;
import com.jambit.testdocker.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public PersonEntity insertPerson(PersonEntity personEntity) throws PersonAlreadyExistException {
        if (repository.findByUsername(personEntity.getUsername()) != null) {
            throw new PersonAlreadyExistException("Person with given Id already exists!");
        }
        return repository.save(personEntity);
    }

    public List<PersonEntity> getAllPersonsList() {
        return repository.findAll();
    }

    public List<PersonEntity> getAllPersonsListByUsername(String username) {
        return repository.findByUsernameContaining(username);
    }

//    public PersonEntity updatePersonById(Long id, PersonEntity person) {
//        Optional<PersonEntity> personFromDb = repository.findById(id);
//
//        personFromDb.map(personEntity -> {
//            personEntity.setAge(person.getAge());
//            personEntity.setBirthDate(person.getBirthDate());
//            personEntity.setGender(person.getGender());
//            personEntity.setFaculty(person.getFaculty());
//            return null;
//        }).orElseThrow(() -> new PersonNotFoundException("User with id : " + id + " not found"));
//
//    }
}
