package com.jambit.testdocker.service;

import com.jambit.testdocker.dto.AddressDto;
import com.jambit.testdocker.dto.UniversityDto;
import com.jambit.testdocker.entity.AddressEntity;
import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.entity.UniversityEntity;
import com.jambit.testdocker.exception.PersonNotFoundException;
import com.jambit.testdocker.exception.UniversityAlreadyExistsException;
import com.jambit.testdocker.exception.UniversityNotFoundException;
import com.jambit.testdocker.repository.PersonRepository;
import com.jambit.testdocker.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;

    private final PersonRepository personRepository;

    private final AddressService addressService;

    public UniversityDto insertUniversity(UniversityDto universityDto)
            throws UniversityAlreadyExistsException {
        UniversityEntity universityEntity = new UniversityEntity();
        mapUniversityDtoToEntity(universityDto, universityEntity);

        if (universityRepository.findByName(universityEntity.getName()) != null) {
            throw new UniversityAlreadyExistsException("University with given name already exists!");
        }

        UniversityEntity savedUniversity = universityRepository.save(universityEntity);
        return mapUniversityEntityToDto(savedUniversity);
    }

    public List<UniversityDto> getAllUniversities() {
        List<UniversityDto> universityDtoList = new ArrayList<>();
        List<UniversityEntity> universityEntityList = universityRepository.findAll();

        universityEntityList.forEach(university -> {
            UniversityDto universityDto = mapUniversityEntityToDto(university);
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

        mapUniversityDtoToEntity(universityDto, universityEntity);
        UniversityEntity university = universityRepository.save(universityEntity);

        return mapUniversityEntityToDto(university);
    }

    public String deleteUniversityById(long id) throws UniversityNotFoundException {
        var universityById = universityRepository.findById(id)
                .orElseThrow(() -> new UniversityNotFoundException("Person with id: " + id + " noy found"));
        if (null != universityById) {
            //fixme write removeAllPersons method in the entity
//            universityById.get().removeAllPersons();
            universityById.getPersons()
                    .forEach(person -> person.getUniversities().remove(universityById));

            universityRepository.deleteById(universityById.getId());

            return "University with id : " + id + " deleted successfully";
        }
        return null;
    }

    private void mapUniversityDtoToEntity(UniversityDto universityDto, UniversityEntity universityEntity) {
        universityEntity.setId(universityDto.getId());
        universityEntity.setName(universityDto.getName());

        AddressEntity addressEntity = new AddressEntity();
        addressService.mapAddressDtoToEntity(universityDto.getAddressDto(), addressEntity);
        universityEntity.setAddress(addressEntity);

        universityDto.getPersons().forEach(person -> {
            PersonEntity personByUsername = personRepository.findByUsername(person);
            if (null == personByUsername) {
                personByUsername = new PersonEntity();
            }
            personByUsername.setUsername(person);
            universityEntity.addPerson(personByUsername);
        });
        universityEntity.setId(universityDto.getId());
    }

    private UniversityDto mapUniversityEntityToDto(UniversityEntity universityEntity) {
        UniversityDto universityDto = new UniversityDto();

        universityDto.setId(universityEntity.getId());
        universityDto.setName(universityEntity.getName());

        AddressDto addressDto = addressService.mapAddressEntityToDto(universityEntity.getAddress());
        universityDto.setAddressDto(addressDto);

        universityDto.setPersons(universityEntity.getPersons().stream()
                .map(PersonEntity::getUsername)
                .collect(Collectors.toSet()));

        return universityDto;
    }

}
