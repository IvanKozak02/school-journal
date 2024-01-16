package com.example.demoauth.repository;

import com.example.demoauth.models.Class;
import com.example.demoauth.models.School;
import com.example.demoauth.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    List<Class> findBySchool(School school);

    Class findByNameOfClassAndSchool(String nameOfClass, School school);

//    Class findByNameOfClassAndNumberOfClassAndSchool(String name,int number,School school);
}
