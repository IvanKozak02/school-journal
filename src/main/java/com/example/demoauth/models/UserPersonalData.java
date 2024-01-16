package com.example.demoauth.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class UserPersonalData {
    @Id
    private String id;
    @Column(nullable=false)
    private String userName;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String dateOfBirth;
    @Column(nullable = false)
    private String workPhone;
    @Column(nullable = false)
    private String sex;
    private String position;
    private String strict;
    private String city;
    private String numberOfHouse;

    @Transient
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @OneToOne(fetch = FetchType.LAZY)
    private UserAuthData userAuthData;

    @ManyToOne
    private School school;

//    @OneToOne(mappedBy = "userPersonalData")
//    private Application application;


    public UserPersonalData() {}


    public UserAuthData getUserAuthData() {
        return userAuthData;
    }

    public void setUserAuthData(UserAuthData userAuthData) {
        this.userAuthData = userAuthData;
    }

    public UserPersonalData(String userName, String email, String dateOfBirth, String workPhone, String sex, String position, String strict, String city, String numberOfHouse, String role, UserAuthData userAuthData, School school) {
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.workPhone = workPhone;
        this.sex = sex;
        this.position = position;
        this.strict = strict;
        this.city = city;
        this.numberOfHouse = numberOfHouse;
        this.role = role;
        this.userAuthData = userAuthData;
        this.school = school;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStrict() {
        return strict;
    }

    public void setStrict(String strict) {
        this.strict = strict;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumberOfHouse() {
        return numberOfHouse;
    }

    public void setNumberOfHouse(String numberOfHouse) {
        this.numberOfHouse = numberOfHouse;
    }
}
