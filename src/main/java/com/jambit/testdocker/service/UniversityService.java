package com.jambit.testdocker.service;

import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.entity.UniversityEntity;
import com.jambit.testdocker.exception.PersonNotFoundException;
import com.jambit.testdocker.exception.UniversityAlreadyExistsException;
import com.jambit.testdocker.exception.UniversityNotFoundException;
import com.jambit.testdocker.repository.PersonRepository;
import com.jambit.testdocker.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;

    private final PersonRepository personRepository;

    public List<UniversityEntity> getAllUniversities() {
        return universityRepository.findAll();
    }

    public UniversityEntity insertUniversity(UniversityEntity university)
            throws UniversityAlreadyExistsException {
        if (universityRepository.findByName(university.getName()) != null) {
            throw new UniversityAlreadyExistsException("University with given name already exists!");
        }

        return universityRepository.save(university);
    }
//
//    public List<UniversityEntity> getUniversitiesByPersonId(long personId) throws PersonNotFoundException {
//        if (!personRepository.existsById(personId)) {
//            throw new PersonNotFoundException("Person with id:" + personId + " not found");
//        }
//
//        return universityRepository.findUniversityEntitiesByPersonsId(personId);
//    }

    public UniversityEntity getUniversityById(long id) throws UniversityNotFoundException {
        return universityRepository.findById(id).orElseThrow(() ->
                new UniversityNotFoundException("University with id: " + " not found"));
    }


}
