package com.example.demoauth.repository;

import com.example.demoauth.models.Class;
import com.example.demoauth.models.HeadTeacher;
import com.example.demoauth.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadTeacherRepository extends JpaRepository<HeadTeacher, Long> {

    HeadTeacher findByTeacher(Teacher teacher);

    HeadTeacher findBySchoolClass(Class schoolClass);

    boolean existsByTeacherAndSchoolClass(Teacher teacher, Class schoolClass);


}
