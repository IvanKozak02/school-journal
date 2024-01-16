package com.example.demoauth.pojo;

public class SchoolData {

    private String name;
    private String oblast;

    private String typeOfSchool;
    private String city;
    private String street;
    private String email;
    private String contactPhone;
    private String webCite;

    private SubjectsList subjects;

    private String viddil;

   // private Administration administration;


    public SchoolData() {
    }


    public String getViddil() {
        return viddil;
    }

    public void setViddil(String viddil) {
        this.viddil = viddil;
    }

    public String getOblast() {
        return oblast;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

    public SubjectsList getSubjects() {
        return subjects;
    }

    public void setSubjects(SubjectsList subjects) {
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfSchool() {
        return typeOfSchool;
    }

    public void setTypeOfSchool(String typeOfSchool) {
        this.typeOfSchool = typeOfSchool;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getWebCite() {
        return webCite;
    }

    public void setWebCite(String webCite) {
        this.webCite = webCite;
    }
}
