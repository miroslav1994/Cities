package com.project.cities.repositories;

import com.project.cities.models.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    CityEntity findByName(String name);
    @Query(value = "select * from cities c " +
            " order by c.num_users desc, c.created_at",
            nativeQuery = true)
    Page<CityEntity> findAllSorted(Pageable pageable);
}
