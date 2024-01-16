package com.example.demoauth.service;

import com.example.demoauth.models.Role;
import com.example.demoauth.models.School;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.repository.UserPersonalDataRepos;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserDataService {


    UserPersonalDataRepos userPersonalDataRepos;

    public UserDataService(UserPersonalDataRepos userPersonalDataRepos) {
        this.userPersonalDataRepos = userPersonalDataRepos;
    }

    public boolean saveNewProfile(UserPersonalData userPersonalData){
        userPersonalData.setId(generateNewId());
        userPersonalDataRepos.save(userPersonalData);
        return true;
    }

    public UserPersonalData getUserByEmail(String email){
       return userPersonalDataRepos.findUserPersonalDataByEmail(email);
    }

    public List<UserPersonalData> findUsersBySchoolAndRole(School school, Set<Role> roleSet){
        return userPersonalDataRepos.findBySchoolAndUserAuthDataRolesIn(school, Collections.singleton(roleSet));
    }

    public UserPersonalData getUserProfile(String id){
        return userPersonalDataRepos.findUserPersonalDataById(id);
    }
    public String generateNewId(){
        return RandomStringUtils.random(8, "0123456789abcdef");
    }

}
