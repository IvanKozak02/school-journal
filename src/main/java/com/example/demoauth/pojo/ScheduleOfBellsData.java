package com.example.demoauth.pojo;

public class ScheduleOfBellsData {

    private String [] numberOfSubject;
    private String[] timeOfStartSubject;
    private String[] timeOfFinishSubject;


    public String[] getNumberOfSubject() {
        return numberOfSubject;
    }

    public void setNumberOfSubject(String[] numberOfSubject) {
        this.numberOfSubject = numberOfSubject;
    }

    public String[] getTimeOfStartSubject() {
        return timeOfStartSubject;
    }

    public void setTimeOfStartSubject(String[] timeOfStartSubject) {
        this.timeOfStartSubject = timeOfStartSubject;
    }

    public String[] getTimeOfFinishSubject() {
        return timeOfFinishSubject;
    }

    public void setTimeOfFinishSubject(String[] timeOfFinishSubject) {
        this.timeOfFinishSubject = timeOfFinishSubject;
    }
}
