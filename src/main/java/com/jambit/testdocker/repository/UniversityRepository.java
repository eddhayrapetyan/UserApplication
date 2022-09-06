package com.jambit.testdocker.repository;

import com.jambit.testdocker.entity.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityRepository extends JpaRepository<UniversityEntity,Long> {
    List<UniversityEntity> findUniversityEntitiesByPersonsId(Long personId);
}
