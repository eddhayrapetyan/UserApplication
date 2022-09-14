package com.jambit.testdocker.service;

import com.jambit.testdocker.dto.PersonDto;
import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.entity.UniversityEntity;
import com.jambit.testdocker.exception.PersonAlreadyExistException;
import com.jambit.testdocker.exception.PersonNotFoundException;
import com.jambit.testdocker.mapper.PersonMapper;
import com.jambit.testdocker.repository.PersonRepository;
import com.jambit.testdocker.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    @Resource
    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    public PersonDto insertPerson(PersonDto personDto) throws PersonAlreadyExistException {
        PersonEntity personEntity = new PersonEntity();
        personMapper.mapDtoToEntity(personDto, personEntity);
        if (personRepository.findByUsername(personEntity.getUsername()) != null) {
            throw new PersonAlreadyExistException("Person with given Id already exists!");
        }

        PersonEntity savedPerson = personRepository.save(personEntity);
        return personMapper.mapEntityToDto(savedPerson);
    }

    public List<PersonDto> getAllPersons() {
        List<PersonDto> personDtoList = new ArrayList<>();
        List<PersonEntity> personEntityList = personRepository.findAll();

        personEntityList.forEach(person -> {
            PersonDto personDto = personMapper.mapEntityToDto(person);
            personDtoList.add(personDto);
        });

        return personDtoList;
    }

    @Transactional
    public PersonDto updatePerson(long id, PersonDto personDto) throws PersonNotFoundException {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person with id: " + id + " not found"));

        personEntity.getUniversities().clear();
        personMapper.mapDtoToEntity(personDto, personEntity);

        PersonEntity person = personRepository.save(personEntity);

        return personMapper.mapEntityToDto(person);
    }

    public String deletePersonById(long id) throws PersonNotFoundException {
        Optional<PersonEntity> personById = Optional.ofNullable(personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person with id: " + id + " noy found")));
        if (personById.isPresent()) {
            personById.get().removeAllUniversities();
            personRepository.deleteById(personById.get().getId());

            return "Student with id : + " + id + " deleted successfully";
        }
        return null;
    }

    public List<PersonDto> getAllPersonsListByUsername(String name) {
        return personRepository.findByFirstNameContaining(name);
    }

    public PersonEntity getPersonById(Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() ->
                new PersonNotFoundException("Person with id : " + id + " not found"));
    }

    public List<PersonEntity> getPersonsByUniId(Long id) {
        return personRepository.findPersonEntitiesByUniversitiesId(id);
    }

}
