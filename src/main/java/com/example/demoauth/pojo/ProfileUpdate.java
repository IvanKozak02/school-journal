package com.example.demoauth.pojo;

public class ProfileUpdate {

    private String username;
    private String phoneNumber;
    private String email;
    private String position;


    public ProfileUpdate() {}

    public ProfileUpdate(String username, String phoneNumber, String email, String position) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
