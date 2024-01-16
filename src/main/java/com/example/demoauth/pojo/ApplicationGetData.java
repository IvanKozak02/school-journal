package com.example.demoauth.pojo;

import java.time.LocalDateTime;

public class ApplicationGetData {

    private String userName;

    private String dateOfCreation;

    public ApplicationGetData() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
}
