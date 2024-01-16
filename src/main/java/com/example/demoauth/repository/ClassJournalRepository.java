package com.example.demoauth.repository;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassJournalRepository extends JpaRepository<ClassJournal, Long> {


    boolean existsBySchedule_Teacher_AndSchedule_SubjectAndSchedule_ClassGroup(Teacher schedule_teacher,
                                                                    Subject schedule_subject,
                                                                    ClassGroup schedule_classGroup);
    boolean existsBySchedule_Teacher_AndSchedule_SubjectAndSchedule_SchoolClass(Teacher schedule_teacher,
                                                                    Subject schedule_subject,
                                                                    Class sc_class);

    List<ClassJournal> findBySchedule_Teacher(Teacher teacher);

    List<ClassJournal> findBySchedule_TeacherAndSchedule_SchoolClassAndSchedule_Subject(Teacher schedule_teacher, Class schedule_schoolClass, Subject schedule_subject);
    List<ClassJournal> findBySchedule_TeacherAndSchedule_ClassGroupAndSchedule_Subject(Teacher schedule_teacher, ClassGroup schedule_classGroup, Subject schedule_subject);

    List<ClassJournal> findBySchedule_SchoolClassAndSchedule_DayOfWeek(Class schedule_schoolClass, String schedule_dayOfWeek);

    List<ClassJournal> findBySchedule_SchoolClass_Id(Long schedule_schoolClass_id);
    List<ClassJournal> findBySchedule_SchoolClass_IdAndSchedule_Subject_Name(Long schedule_schoolClass_id, String schedule_subject_name);
    List<ClassJournal> findBySchedule_SchoolClass_IdAndSchedule_Subject_NameAndSchedule_ClassGroup_NumberOfGroup(Long schedule_schoolClass_id, String schedule_subject_name, int schedule_classGroup_numberOfGroup);

    boolean existsBySchedule(Schedule schedule);

    ClassJournal findBySchedule(Schedule schedule);
}
