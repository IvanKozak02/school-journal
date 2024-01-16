package com.example.demoauth.pojo;

public class ClassProfileData {

    private long classId;

    private String cabinet;

    private String newHeadTeacherId;


    public String getNewHeadTeacherId() {
        return newHeadTeacherId;
    }

    public void setNewHeadTeacherId(String newHeadTeacherId) {
        this.newHeadTeacherId = newHeadTeacherId;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }
}
