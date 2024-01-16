package com.example.demoauth.models;

import javax.persistence.*;

@Entity
public class CalendarPlanning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String dateOfLesson;
    private String lessonTopic;

    private String homework;

    private String dateOfHomework;

    @ManyToOne
    private ClassJournal classJournal;

    private String semester;


    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDateOfLesson() {
        return dateOfLesson;
    }

    public void setDateOfLesson(String dateOfLesson) {
        this.dateOfLesson = dateOfLesson;
    }

    public String getLessonTopic() {
        return lessonTopic;
    }

    public void setLessonTopic(String lessonTopic) {
        this.lessonTopic = lessonTopic;
    }


    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public String getDateOfHomework() {
        return dateOfHomework;
    }

    public void setDateOfHomework(String dateOfHomework) {
        this.dateOfHomework = dateOfHomework;
    }

    public ClassJournal getClassJournal() {
        return classJournal;
    }

    public void setClassJournal(ClassJournal classJournal) {
        this.classJournal = classJournal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
