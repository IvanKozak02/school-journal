package com.example.demoauth.pojo;

public class CalendarPlanningData {

    private String journalId;
    private String dateOfLesson;
    private String lessonTopic;
    private String homework;
    private String dateOfHomework;

    private String semester;

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

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
}
