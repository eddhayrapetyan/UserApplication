package com.jambit.testdocker.service;

import com.jambit.testdocker.dto.PersonDto;
import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.entity.UniversityEntity;
import com.jambit.testdocker.exception.PersonAlreadyExistException;
import com.jambit.testdocker.exception.PersonNotFoundException;
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

    @Resource
    private final UniversityRepository universityRepository;

    public PersonDto addPerson(PersonDto personDto) {
        PersonEntity person = new PersonEntity();
        mapDtoToEntity(personDto, person);
        PersonEntity savedPerson = personRepository.save(person);
        return mapEntityToDto(savedPerson);
    }

    public List<PersonDto> getAllPersons() {
        List<PersonDto> personDtoList = new ArrayList<>();
        List<PersonEntity> personEntityList = personRepository.findAll();

        personEntityList.forEach(person -> {
            PersonDto personDto = mapEntityToDto(person);
            personDtoList.add(personDto);
        });

        return personDtoList;
    }

    @Transactional
    public PersonDto updatePerson(long id, PersonDto personDto) throws PersonNotFoundException {
        PersonEntity personEntity = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person with id: " + id + " noy found"));

        personEntity.getUniversities().clear();
        mapDtoToEntity(personDto, personEntity);

        PersonEntity person = personRepository.save(personEntity);

        return mapEntityToDto(person);
    }

    public String deletePerson(long id) throws PersonNotFoundException {
        Optional<PersonEntity> personById = Optional.ofNullable(personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person with id: " + id + " noy found")));
        if (personById.isPresent()) {
            personById.get().removeAllUniversities();
            personRepository.deleteById(personById.get().getId());

            return "Student with id : + " + id + " deleted successfully";
        }
        return null;
    }

    public PersonEntity insertPerson(PersonEntity personEntity) throws PersonAlreadyExistException {
        if (personRepository.findByUsername(personEntity.getUsername()) != null) {
            throw new PersonAlreadyExistException("Person with given Id already exists!");
        }
        return personRepository.save(personEntity);
    }

    public List<PersonEntity> findAllPersons() {
        return personRepository.findAll();
    }

    public List<PersonEntity> getAllPersonsListByUsername(String username) {
        return personRepository.findByUsernameContaining(username);
    }

    public PersonEntity getPersonById(Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() ->
                new PersonNotFoundException("Person with id : " + id + " not found"));
    }

    public List<PersonEntity> getPersonsByUniId(Long id) {
        return personRepository.findPersonEntitiesByUniversitiesId(id);
    }

    public PersonEntity updatePersonById(Long id, PersonEntity person) throws PersonNotFoundException {
        var personById = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person with id : " + id + " not found"));

        personById.setAge(person.getAge());
        personById.setBirthDate(person.getBirthDate());
        personById.setGender(person.getGender());
        personById.setFaculty(person.getFaculty());

        return personRepository.save(personById);
    }

    public void deletePersonById(long id) throws PersonNotFoundException {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            throw new PersonNotFoundException("Person with id : " + id + " not found");
        }
    }

    private void mapDtoToEntity(PersonDto personDto, PersonEntity personEntity) {
        personEntity.setName(personDto.getName());
        personEntity.setUsername(personDto.getUsername());
        personEntity.setPassword(personDto.getPassword());
        personEntity.setFaculty(personDto.getFaculty());
        personEntity.setBirthDate(personDto.getBirthDate());
        personEntity.setAge(personDto.getAge());
        personEntity.setGender(personDto.getGender());

        personDto.getUniversities().forEach(uni -> {
            UniversityEntity university = universityRepository.findByName(uni);
            if (null == university) {
                university = new UniversityEntity();
            }
            university.setName(uni);
            personEntity.addUniversity(university);
        });
    }

    private PersonDto mapEntityToDto(PersonEntity personEntity) {
        PersonDto responseDto = new PersonDto();

        responseDto.setName(personEntity.getName());
        responseDto.setUsername(personEntity.getUsername());
        responseDto.setPassword(personEntity.getPassword());
        responseDto.setFaculty(personEntity.getFaculty());
        responseDto.setBirthDate(personEntity.getBirthDate());
        responseDto.setAge(personEntity.getAge());
        responseDto.setGender(personEntity.getGender());

        responseDto.setUniversities(personEntity.getUniversities()
                .stream().map(UniversityEntity::getName)
                .collect(Collectors.toSet()));

        return responseDto;
    }


}
