package com.example.demoauth.repository;

import com.example.demoauth.models.ScheduleOfBells;
import com.example.demoauth.models.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleOfBellsRepository extends JpaRepository<ScheduleOfBells, Long> {

    ScheduleOfBells findBySchool(School school);

    boolean existsBySchool(School school);
}
