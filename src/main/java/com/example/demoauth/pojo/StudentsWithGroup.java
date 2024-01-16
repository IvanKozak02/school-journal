package com.example.demoauth.pojo;

import com.example.demoauth.models.Student;

import java.util.List;

public class StudentsWithGroup {

    private List<Student> students;

    private int numberOfGroup;

    public StudentsWithGroup(List<Student> students, int numberOfGroup) {
        this.students = students;
        this.numberOfGroup = numberOfGroup;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getNumberOfGroup() {
        return numberOfGroup;
    }

    public void setNumberOfGroup(int numberOfGroup) {
        this.numberOfGroup = numberOfGroup;
    }
}
