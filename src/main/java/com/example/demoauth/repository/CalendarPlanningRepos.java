package com.example.demoauth.repository;

import com.example.demoauth.models.CalendarPlanning;
import com.example.demoauth.models.ClassJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarPlanningRepos extends JpaRepository<CalendarPlanning, Long> {
    List<CalendarPlanning> findByClassJournal(ClassJournal classJournal);

    boolean existsByDateOfLessonAndClassJournal(String dateOfLesson, ClassJournal classJournal);

    CalendarPlanning findByDateOfLessonAndClassJournal(String dateOfLesson, ClassJournal classJournal);

    CalendarPlanning findByDateOfLessonAndClassJournalAndLessonTopic(String dateOfLesson, ClassJournal classJournal, String lessonTopic);


    CalendarPlanning findByClassJournalAndDateOfLessonAndLessonTopicAndHomeworkAndDateOfHomework(ClassJournal classJournal, String dateOfLesson, String lessonTopic, String homework, String dateOfHomework);

    List<CalendarPlanning> findByClassJournalAndSemester(ClassJournal classJournal, String semester);
}
