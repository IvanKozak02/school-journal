package com.example.demoauth.repository;

import com.example.demoauth.models.*;
import com.example.demoauth.models.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTeacher(Teacher teacher);


    boolean existsBySchoolClassAndSubjectAndLessonNumberAndDayOfWeekAndClassGroup(Class scClass, Subject subject, String lessonNumber, String dayOfWeek,
                                                                                  ClassGroup classGroup);

    Schedule findBySchoolClassAndSubjectAndLessonNumberAndDayOfWeekAndClassGroup(Class scClass, Subject subject, String lessonNumber, String dayOfWeek,
                                                                                 ClassGroup classGroup);

    boolean existsBySchoolClassAndDayOfWeekAndLessonNumber(Class scClass, String dayOfWeek, String lessonNumber);

    boolean existsBySchoolClassAndDayOfWeekAndLessonNumberAndClassGroupAndPeriod(Class scClass, String dayOfWeek, String lessonNumber,
                                                                                 ClassGroup classGroup, String period);

    @Transactional
    void deleteByTeacherAndSubjectAndSchoolClassAndClassGroupAndDayOfWeekAndLessonNumberAndPeriod(Teacher teacher, Subject subject,
                                                                                                  Class schoolClass, ClassGroup classGroup, String dayOfWeek,
                                                                                                  String lessonNumber, String period);

    List<Schedule> findByTeacherAndClassGroupAndSubject(Teacher schedule_teacher, ClassGroup schedule_classGroup, Subject schedule_subject);
    List<Schedule> findByTeacherAndSchoolClassAndSubject(Teacher schedule_teacher, Class schedule_schoolClass, Subject schedule_subject);

    List<Schedule> findBySchoolClassAndDayOfWeek(Class sClass, String day);

    boolean existsScheduleByClassGroup(ClassGroup classGroup);

    List<Schedule> findBySchoolClass_Id(long classId);
}
