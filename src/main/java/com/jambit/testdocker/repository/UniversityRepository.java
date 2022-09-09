package com.jambit.testdocker.repository;

import com.jambit.testdocker.entity.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {

//    List<UniversityEntity> findUniversityEntitiesByPersonsId(Long personId);

    UniversityEntity findByName(String name);

    @Transactional
    void deleteById(long id);

//    @Transactional
//    void deleteByPersonId(long id);

}
