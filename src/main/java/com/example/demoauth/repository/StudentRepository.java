package com.example.demoauth.repository;

import com.example.demoauth.models.Class;
import com.example.demoauth.models.School;
import com.example.demoauth.models.Student;
import com.example.demoauth.models.UserPersonalData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {


    Student findByUserPersonalData(UserPersonalData userPersonalData);

    List<Student> findByUserPersonalDataSchool(School school);

//    Set<Student> findByClassesIn(Set<Class> classes);

    List<Student> findBySchoolClass(Class schoolClass);

    List<Student> findByUserPersonalData_IdIn(List<String> ids);

}