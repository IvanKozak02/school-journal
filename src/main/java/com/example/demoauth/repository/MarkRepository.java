package com.example.demoauth.repository;

import com.example.demoauth.models.Class;
import com.example.demoauth.models.ClassJournal;
import com.example.demoauth.models.Mark;
import com.example.demoauth.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    Mark findByDateAndStudent_IdAndClassJournal_Id(String date, Long student_id, Long classJournal_id);

    boolean existsMarkByDateAndColNameAndStudentAndClassJournal(String date, String colName, Student student, ClassJournal classJournal);

    Mark findByDateAndColNameAndStudentAndClassJournal(String date,String colName, Student student, ClassJournal classJournal);

    List<Mark> findByClassJournal(ClassJournal classJournal);

    List<Mark> findByStudent_SchoolClassAndMarkBetween(Class student_schoolClass, int mark, int mark2);

    List<Mark> findByStudentAndDateAndMarkBetween(Student student, String date, int mark, int mark2);
    List<Mark> findByStudentAndDate(Student student, String date);

    void deleteByStudent(Student student);
}
