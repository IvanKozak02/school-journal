package com.example.demoauth.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "subjects", cascade = CascadeType.ALL)
    private Set<Class> classes;

    public Subject() {

    }

    public Set<Class> getClasses() {
        return classes;
    }

    public void setClasses(Set<Class> classes) {
        this.classes = classes;
    }

    //    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            },mappedBy = "subjects")
//    @JsonIgnore
//    private Set<School> schools = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            })
//    @JsonIgnore
//    private Set<Teacher> teachers = new HashSet<>();

//
//    public Set<School> getSchools() {
//        return schools;
//    }
//
//    public void setSchools(Set<School> schools) {
//        this.schools = schools;
//    }
//
//    public Subject() {
//
//    }
//
//    public Set<Teacher> getTeachers() {
//        return teachers;
//    }
//
//    public void setTeachers(Set<Teacher> teachers) {
//        this.teachers = teachers;
//    }

    public String getName() {
        return name;
    }

    public Subject(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
