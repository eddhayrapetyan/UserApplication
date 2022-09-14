package com.jambit.testdocker.mapper;

import com.jambit.testdocker.dto.AddressDto;
import com.jambit.testdocker.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public void mapAddressDtoToEntity(AddressDto addressDto, AddressEntity addressEntity) {
        addressEntity.setId(addressDto.getId());
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setBuilding(addressDto.getBuilding());
    }

    public AddressDto mapAddressEntityToDto(AddressEntity addressEntity) {
        AddressDto addressDto = new AddressDto();

        addressDto.setId(addressEntity.getId());
        addressDto.setCity(addressEntity.getCity());
        addressDto.setStreet(addressEntity.getStreet());
        addressDto.setBuilding(addressEntity.getBuilding());

        return addressDto;
    }
}
