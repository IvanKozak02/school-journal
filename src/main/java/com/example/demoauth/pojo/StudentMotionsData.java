package com.example.demoauth.pojo;

import com.example.demoauth.models.Class;

import javax.persistence.ManyToOne;

public class StudentMotionsData {

    private String characteristic;

    private String period;

    private String countOfStudents;

    private String classId;


    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCountOfStudents() {
        return countOfStudents;
    }

    public void setCountOfStudents(String countOfStudents) {
        this.countOfStudents = countOfStudents;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
