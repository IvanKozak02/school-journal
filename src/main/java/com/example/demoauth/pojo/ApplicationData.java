package com.example.demoauth.pojo;

import org.springframework.web.multipart.MultipartFile;

public class ApplicationData {

    private String typeOfApplication;

    private String schoolName;

    private String subjects;

    public ApplicationData() {
    }

    public ApplicationData(String typeOfApplication, String schoolName, String subjects) {
        this.typeOfApplication = typeOfApplication;
        this.schoolName = schoolName;
        this.subjects = subjects;
    }

    public String getTypeOfApplication() {
        return typeOfApplication;
    }

    public void setTypeOfApplication(String typeOfApplication) {
        this.typeOfApplication = typeOfApplication;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }


}
