package com.example.demoauth.models;

import javax.persistence.*;

@Entity
public class Certificates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_personal_data_id", nullable = false)
    private UserPersonalData userPersonalData;


    public UserPersonalData getUserPersonalData() {
        return userPersonalData;
    }

    public void setUserPersonalData(UserPersonalData userPersonalData) {
        this.userPersonalData = userPersonalData;
    }

    public Certificates() {
    }

    public Certificates(String imgUrl, UserPersonalData userPersonalData) {
        this.imgUrl = imgUrl;
        this.userPersonalData = userPersonalData;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
