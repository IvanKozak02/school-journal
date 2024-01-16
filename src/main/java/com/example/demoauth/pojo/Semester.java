package com.example.demoauth.pojo;

import com.example.demoauth.models.School;

public class Semester {

    private String semStart;
    private String semFinish;
    private String nameOfSem;


    public String getNameOfSem() {
        return nameOfSem;
    }

    public void setNameOfSem(String nameOfSem) {
        this.nameOfSem = nameOfSem;
    }

    public String getSemStart() {
        return semStart;
    }

    public void setSemStart(String semStart) {
        this.semStart = semStart;
    }

    public String getSemFinish() {
        return semFinish;
    }

    public void setSemFinish(String semFinish) {
        this.semFinish = semFinish;
    }



}
