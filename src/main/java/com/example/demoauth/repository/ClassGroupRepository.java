package com.example.demoauth.repository;

import com.example.demoauth.models.Class;
import com.example.demoauth.models.ClassGroup;
import com.example.demoauth.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {

    boolean existsBySubjectAndSchoolClass(Subject subject, Class schoolClass);

    boolean existsBySchoolClass(Class schoolClass);

    int countBySubject(Subject subject);

    List<ClassGroup> findBySchoolClassAndSubject(Class scClass, Subject subject);

    @Transactional
    void deleteClassGroupsBySchoolClassAndSubject(Class scClass, Subject subject);

    boolean existsBySchoolClassAndNumberOfGroupAndSubject(Class scClass, int numberOfGroup, Subject subject);

    ClassGroup findBySchoolClassAndNumberOfGroupAndSubject(Class scClass, int numberOfGroup, Subject subject);

    @Transactional
    void deleteByNumberOfGroupAndSchoolClassAndSubject(int numberOfGroup, Class scClass, Subject subject);

    List<ClassGroup> findBySchoolClass(Class scClass);

}
