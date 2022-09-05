package com.jambit.testdocker.repository;

import com.jambit.testdocker.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface PersonRepository extends CrudRepository<PersonEntity,Long> {
    PersonEntity findByUsername(String username);
}
