package com.example.demoauth.models;

import javax.persistence.*;

@Entity
public class StructureOfYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String startOfSem;

    private String finishOfSem;

    private String nameOfSem;

    @OneToOne
    private School school;

    public String getNameOfSem() {
        return nameOfSem;
    }

    public void setNameOfSem(String nameOfSem) {
        this.nameOfSem = nameOfSem;
    }

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

    public String getStartOfSem() {
        return startOfSem;
    }

    public void setStartOfSem(String startOfSem) {
        this.startOfSem = startOfSem;
    }

    public String getFinishOfSem() {
        return finishOfSem;
    }

    public void setFinishOfSem(String finishOfSem) {
        this.finishOfSem = finishOfSem;
    }
}
