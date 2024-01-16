package com.example.demoauth.repository;

import com.example.demoauth.models.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School,Long> {

//    School findById(Long id);

    School findByName(String name);

    boolean existsByName(String name);

}
