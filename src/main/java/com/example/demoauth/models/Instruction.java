package com.example.demoauth.models;


import javax.persistence.*;

@Entity
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    private String instructionSubject;

    private String instructionDate;

    @ManyToOne
    private Class instructionClass;


    public String getInstructionSubject() {
        return instructionSubject;
    }

    public void setInstructionSubject(String instructionSubject) {
        this.instructionSubject = instructionSubject;
    }

    public String getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(String instructionDate) {
        this.instructionDate = instructionDate;
    }

    public Class getInstructionClass() {
        return instructionClass;
    }

    public void setInstructionClass(Class instructionClass) {
        this.instructionClass = instructionClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
