package com.example.demoauth.pojo;

import com.example.demoauth.models.Subject;

import java.util.Set;

public class SubjectsList {


    private Set<Subject> subjects;


    public SubjectsList() {
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
