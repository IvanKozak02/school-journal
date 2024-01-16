package com.example.demoauth.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@Entity

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String parentsName;

    private String parentsPhones;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_personal_data_id", referencedColumnName = "id")
    private UserPersonalData userPersonalData;


    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)

    private Set<Class> classes;

    @OneToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Class schoolClass;


    public UserPersonalData getUserPersonalData() {
        return userPersonalData;
    }

    public void setUserPersonalData(UserPersonalData userPersonalData) {
        this.userPersonalData = userPersonalData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentsName() {
        return parentsName;
    }

    public void setParentsName(String parentsName) {
        this.parentsName = parentsName;
    }

    public String getParentsPhones() {
        return parentsPhones;
    }

    public void setParentsPhones(String parentsPhones) {
        this.parentsPhones = parentsPhones;
    }
//    @JsonManagedReference
    public Set<Class> getClasses() {
        return classes;
    }

    public void setClasses(Set<Class> classes) {
        this.classes = classes;
    }

    public Class getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(Class schoolClass) {
        this.schoolClass = schoolClass;
    }
}
