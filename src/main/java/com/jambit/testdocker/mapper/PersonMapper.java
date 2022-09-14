package com.jambit.testdocker.mapper;

import com.jambit.testdocker.dto.PersonDto;
import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.entity.UniversityEntity;
import com.jambit.testdocker.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PersonMapper {
    @Resource
    private final UniversityRepository universityRepository;

    public void mapDtoToEntity(PersonDto personDto, PersonEntity personEntity) {
        personEntity.setFirstName(personDto.getFirstName());
        personEntity.setLastName(personDto.getFirstName());
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

    public PersonDto mapEntityToDto(PersonEntity personEntity) {
        PersonDto personDto = new PersonDto();

        personDto.setFirstName(personEntity.getFirstName());
        personDto.setLastName(personEntity.getLastName());
        personDto.setUsername(personEntity.getUsername());
        personDto.setPassword(personEntity.getPassword());
        personDto.setFaculty(personEntity.getFaculty());
        personDto.setBirthDate(personEntity.getBirthDate());
        personDto.setAge(personEntity.getAge());
        personDto.setGender(personEntity.getGender());

        personDto.setUniversities(personEntity.getUniversities()
                .stream().map(UniversityEntity::getName)
                .collect(Collectors.toSet()));

        return personDto;
    }
}
