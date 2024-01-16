package com.example.demoauth.models;

import javax.persistence.*;

@Entity
public class AdditionalJournalColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String columnName;

    private boolean canAdd;

    private String date;

    @ManyToOne
    private ClassJournal classJournal;


    public ClassJournal getClassJournal() {
        return classJournal;
    }

    public void setClassJournal(ClassJournal classJournal) {
        this.classJournal = classJournal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
