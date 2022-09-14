package com.jambit.testdocker.service;

import com.jambit.testdocker.dto.UniversityDto;
import com.jambit.testdocker.entity.UniversityEntity;
import com.jambit.testdocker.exception.PersonNotFoundException;
import com.jambit.testdocker.exception.UniversityAlreadyExistsException;
import com.jambit.testdocker.exception.UniversityNotFoundException;
import com.jambit.testdocker.mapper.UniversityMapper;
import com.jambit.testdocker.repository.PersonRepository;
import com.jambit.testdocker.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniversityService {
    @Resource
    private final UniversityRepository universityRepository;

    @Resource
    private final PersonRepository personRepository;

    private final UniversityMapper universityMapper;

    public UniversityDto insertUniversity(UniversityDto universityDto)
            throws UniversityAlreadyExistsException {
        UniversityEntity universityEntity = new UniversityEntity();
        universityMapper.mapUniversityDtoToEntity(universityDto, universityEntity);

        if (universityRepository.findByName(universityEntity.getName()) != null) {
            throw new UniversityAlreadyExistsException("University with given name already exists!");
        }

        UniversityEntity savedUniversity = universityRepository.save(universityEntity);
        return universityMapper.mapUniversityEntityToDto(savedUniversity);
    }

    public List<UniversityDto> getAllUniversities() {
        List<UniversityDto> universityDtoList = new ArrayList<>();
        List<UniversityEntity> universityEntityList = universityRepository.findAll();

        universityEntityList.forEach(university -> {
            UniversityDto universityDto = universityMapper.mapUniversityEntityToDto(university);
            universityDtoList.add(universityDto);
        });

        return universityDtoList;
    }

    public List<UniversityEntity> getUniversitiesByPersonId(long personId) throws PersonNotFoundException {
        if (!personRepository.existsById(personId)) {
            throw new PersonNotFoundException("Person with id:" + personId + " not found");
        }

        return universityRepository.findUniversityEntitiesByPersons(personId);
    }

    public UniversityEntity getUniversityById(long id) throws UniversityNotFoundException {
        return universityRepository.findById(id).orElseThrow(() ->
                new UniversityNotFoundException("University with id: " + " not found"));
    }

    public UniversityDto updateUniversity(long id, UniversityDto universityDto) throws UniversityNotFoundException {
        UniversityEntity universityEntity = universityRepository.findById(id).orElseThrow(() ->
                new UniversityNotFoundException("University with id: " + id + " was not found"));
        universityEntity.getPersons().clear();

        universityMapper.mapUniversityDtoToEntity(universityDto, universityEntity);
        UniversityEntity university = universityRepository.save(universityEntity);

        return universityMapper.mapUniversityEntityToDto(university);
    }

    public String deleteUniversityById(long id) throws UniversityNotFoundException {
        var universityById = Optional.ofNullable(universityRepository.findById(id)
                .orElseThrow(() -> new UniversityNotFoundException("Person with id: " + id + " noy found")));
        if (universityById.isPresent()) {
            //fixme write mardavari removeAllPersons method in the entity
//            universityById.get().removeAllPersons();
            UniversityEntity university = universityById.get();

            university.getPersons()
                    .forEach(person -> person.getUniversities().remove(universityById));

            universityRepository.deleteById(university.getId());

            return "University with id : " + id + " deleted successfully";
        }
        return null;
    }

}
