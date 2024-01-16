package com.example.demoauth.models;

import javax.persistence.*;

@Entity
public class ScheduleOfBells {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String numberOfSubject;

    private String timeOfStart;

    private String timeOfFinish;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getNumberOfSubject() {
        return numberOfSubject;
    }

    public void setNumberOfSubject(String numberOfSubject) {
        this.numberOfSubject = numberOfSubject;
    }

    public String getTimeOfStart() {
        return timeOfStart;
    }

    public void setTimeOfStart(String timeOfStart) {
        this.timeOfStart = timeOfStart;
    }

    public String getTimeOfFinish() {
        return timeOfFinish;
    }

    public void setTimeOfFinish(String timeOfFinish) {
        this.timeOfFinish = timeOfFinish;
    }
}
