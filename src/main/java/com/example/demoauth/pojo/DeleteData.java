package com.example.demoauth.pojo;

import java.util.List;

public class DeleteData {

    private String classId;

    private String subjectName;

    private List<StudentAndPidgroup> students;


    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<StudentAndPidgroup> getStudents() {
        return students;
    }

    public void setStudents(List<StudentAndPidgroup> students) {
        this.students = students;
    }
}
