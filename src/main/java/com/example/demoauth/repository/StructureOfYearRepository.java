package com.example.demoauth.repository;

import com.example.demoauth.models.School;
import com.example.demoauth.models.StructureOfYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureOfYearRepository extends JpaRepository<StructureOfYear, Long> {

    StructureOfYear findByNameOfSemAndSchool(String nameOfSem,School school);
}
