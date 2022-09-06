package com.jambit.testdocker.repository;

import com.jambit.testdocker.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity,Long> {
    PersonEntity findByUsername(String username);

    List<PersonEntity> findByUsernameContaining(String username);

    List<PersonEntity> findPersonEntitiesByUniversitiesId(Long universityId);
}
