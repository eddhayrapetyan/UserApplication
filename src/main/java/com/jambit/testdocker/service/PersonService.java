package com.jambit.testdocker.service;

import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.exception.PersonAlreadyExistException;
import com.jambit.testdocker.repository.PersonRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public PersonEntity insertPerson(PersonEntity personEntity) throws PersonAlreadyExistException {
        if(repository.findByUsername(personEntity.getUsername()) != null){
            throw new PersonAlreadyExistException("Person with given Id already exists!");
        }
        return repository.save(personEntity);
    }
}
