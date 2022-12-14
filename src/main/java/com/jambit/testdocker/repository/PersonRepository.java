package com.jambit.testdocker.repository;

import com.jambit.testdocker.dto.PersonDto;
import com.jambit.testdocker.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,Long> {
    PersonEntity findByUsername(String username);

    List<PersonDto> findByFirstNameContaining(String name);

    List<PersonEntity> findPersonEntitiesByUniversitiesId(Long universityId);
}
