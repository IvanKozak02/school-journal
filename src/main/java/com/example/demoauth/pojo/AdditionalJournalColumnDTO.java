package com.example.demoauth.pojo;

import com.example.demoauth.models.ClassJournal;

public class AdditionalJournalColumnDTO {

    private String columnName;

    private boolean canAdd;

    private String date;

    private String classJournal;

    public String getClassJournal() {
        return classJournal;
    }

    public void setClassJournal(String classJournal) {
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
}
