package com.example.demoauth.pojo;

public class ColumnDTO {
    private String columnName;

    private boolean canAdd;

    private String date;

    private String classJournal;

    private String colId;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClassJournal() {
        return classJournal;
    }

    public void setClassJournal(String classJournal) {
        this.classJournal = classJournal;
    }

    public String getColId() {
        return colId;
    }

    public void setColId(String colId) {
        this.colId = colId;
    }
}
