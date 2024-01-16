package com.example.demoauth.repository;

import com.example.demoauth.models.School;
import com.example.demoauth.models.SchoolCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolCalendarRepository extends JpaRepository<SchoolCalendar, Long> {

    List<SchoolCalendar> findByEventTypeAndSchool(String eventType, School school);

    List<SchoolCalendar> findBySchool(School school);

    SchoolCalendar findByEventNameAndDateOfStartEvent(String eventName, String dateOfStartEvent);

}
