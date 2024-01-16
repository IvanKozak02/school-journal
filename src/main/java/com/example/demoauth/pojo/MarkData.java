package com.example.demoauth.pojo;


public class MarkData {

    private int mark;

    private String student;

    private String typeOfMark;

    private long classJournal;

    private String colName;

    private String date;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getTypeOfMark() {
        return typeOfMark;
    }

    public void setTypeOfMark(String typeOfMark) {
        this.typeOfMark = typeOfMark;
    }

    public long getClassJournal() {
        return classJournal;
    }

    public void setClassJournal(long classJournal) {
        this.classJournal = classJournal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
