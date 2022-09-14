package com.jambit.testdocker.mapper;

import com.jambit.testdocker.dto.AddressDto;
import com.jambit.testdocker.dto.UniversityDto;
import com.jambit.testdocker.entity.AddressEntity;
import com.jambit.testdocker.entity.PersonEntity;
import com.jambit.testdocker.entity.UniversityEntity;
import com.jambit.testdocker.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UniversityMapper {
    private final AddressMapper addressMapper;

    private final PersonRepository personRepository;

    public void mapUniversityDtoToEntity(UniversityDto universityDto, UniversityEntity universityEntity) {
        universityEntity.setId(universityDto.getId());
        universityEntity.setName(universityDto.getName());

        AddressEntity addressEntity = new AddressEntity();
        addressMapper.mapAddressDtoToEntity(universityDto.getAddressDto(), addressEntity);
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

    public UniversityDto mapUniversityEntityToDto(UniversityEntity universityEntity) {
        UniversityDto universityDto = new UniversityDto();

        universityDto.setId(universityEntity.getId());
        universityDto.setName(universityEntity.getName());

        AddressDto addressDto = addressMapper.mapAddressEntityToDto(universityEntity.getAddress());
        universityDto.setAddressDto(addressDto);

        universityDto.setPersons(universityEntity.getPersons().stream()
                .map(PersonEntity::getUsername)
                .collect(Collectors.toSet()));

        return universityDto;
    }
}
