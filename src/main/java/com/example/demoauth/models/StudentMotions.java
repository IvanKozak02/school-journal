package com.example.demoauth.models;

import javax.persistence.*;

@Entity
public class StudentMotions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String characteristic;

    private String countFirstSem;
    private String countSecondSem;
    private String countYear;

    @ManyToOne
    private Class scClass;


    public StudentMotions() {
    }


    public StudentMotions(String characteristic, String countFirstSem, String countSecondSem, String countYear, Class scClass) {
        this.characteristic = characteristic;
        this.countFirstSem = countFirstSem;
        this.countSecondSem = countSecondSem;
        this.countYear = countYear;
        this.scClass = scClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Class getScClass() {
        return scClass;
    }

    public void setScClass(Class scClass) {
        this.scClass = scClass;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getCountFirstSem() {
        return countFirstSem;
    }

    public void setCountFirstSem(String countFirstSem) {
        this.countFirstSem = countFirstSem;
    }

    public String getCountSecondSem() {
        return countSecondSem;
    }

    public void setCountSecondSem(String countSecondSem) {
        this.countSecondSem = countSecondSem;
    }

    public String getCountYear() {
        return countYear;
    }

    public void setCountYear(String countYear) {
        this.countYear = countYear;
    }
}
