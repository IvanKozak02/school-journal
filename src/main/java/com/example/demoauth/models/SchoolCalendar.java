package com.example.demoauth.models;

import javax.persistence.*;

@Entity
public class SchoolCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String eventType;

    private String eventName;

    private String dateOfStartEvent;

    private String dateOfFinishEvent;

    private String eventColor;

    @OneToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDateOfStartEvent() {
        return dateOfStartEvent;
    }

    public void setDateOfStartEvent(String dateOfStartEvent) {
        this.dateOfStartEvent = dateOfStartEvent;
    }

    public String getDateOfFinishEvent() {
        return dateOfFinishEvent;
    }

    public void setDateOfFinishEvent(String dateOfFinishEvent) {
        this.dateOfFinishEvent = dateOfFinishEvent;
    }

    public String getEventColor() {
        return eventColor;
    }

    public void setEventColor(String eventColor) {
        this.eventColor = eventColor;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
