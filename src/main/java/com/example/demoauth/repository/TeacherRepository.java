package com.example.demoauth.repository;

import com.example.demoauth.models.Class;
import com.example.demoauth.models.School;
import com.example.demoauth.models.Teacher;
import com.example.demoauth.models.UserPersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByUserPersonalData(UserPersonalData userPersonalData);


    Teacher findByUserPersonalData(UserPersonalData userPersonalData);

    List<Teacher> findByUserPersonalDataSchool(School school);


    List<Teacher> findByClassesIn(List<Class> classes);



}
