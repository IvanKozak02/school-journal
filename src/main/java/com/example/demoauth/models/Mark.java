package com.example.demoauth.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int mark;

    @ManyToOne
    private Student student;

    private String typeOfMark;

    @ManyToOne
    private ClassJournal classJournal;

    private String colName;

    private String date;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public ClassJournal getClassJournal() {
        return classJournal;
    }

    public void setClassJournal(ClassJournal classJournal) {
        this.classJournal = classJournal;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getTypeOfMark() {
        return typeOfMark;
    }

    public void setTypeOfMark(String typeOfMark) {
        this.typeOfMark = typeOfMark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
