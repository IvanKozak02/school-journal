package com.example.demoauth.service;

import com.example.demoauth.models.*;
import com.example.demoauth.pojo.AdministrationDetails;
import com.example.demoauth.pojo.ID;
import com.example.demoauth.repository.AdministrationRepos;
import com.example.demoauth.repository.RoleRepository;
import com.example.demoauth.repository.UserPersonalDataRepos;
import com.example.demoauth.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdministrationService {

    private AdministrationRepos administrationRepos;

    private UserPersonalDataRepos userPersonalDataRepos;

    private MessageService messageService;

    private RoleRepository roleRepository;

    private UserRepository userRepository;
    ObjectMapper objectMapper = new ObjectMapper();


    public AdministrationService(AdministrationRepos administrationRepos, UserPersonalDataRepos userPersonalDataRepos, MessageService messageService, RoleRepository roleRepository, UserRepository userRepository) {
        this.administrationRepos = administrationRepos;
        this.userPersonalDataRepos = userPersonalDataRepos;
        this.messageService = messageService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void createNewAdministrator(UserPersonalData userPersonalData){
        Administration administration = new Administration();
        administration.setName(userPersonalData.getUserName());
        administration.setSchool(userPersonalData.getSchool());
        administration.setPosition(userPersonalData.getPosition());
        administration.setUserPersonalData(userPersonalData);
        administrationRepos.save(administration);
        messageService.saveMessage(Collections.singletonList(userPersonalData),"Вам надано статус адміністратора!!!");
    }

    public List<UserPersonalData> getUserPersonalData(School school){
        List<Administration> administration = administrationRepos.findBySchool(school);
        List<UserPersonalData> userPersonalDataList = new ArrayList<>();
        for (Administration value : administration) {
            userPersonalDataList.add(value.getUserPersonalData());
        }
        return userPersonalDataList;
    }


    public void updateAdmin(String jsonData) throws JsonProcessingException {
        AdministrationDetails administrationDetails = objectMapper.readValue(jsonData,AdministrationDetails.class);
        Administration administration = administrationRepos.findById(Long.parseLong(administrationDetails.getId())).get();
        administration.setPosition(administrationDetails.getPosition());
        administration.setName(administration.getName());
        administrationRepos.save(administration);
    }
    public List<Administration> getAdministrationOfSchool(School school){
        return administrationRepos.findBySchool(school);
    }


    public void deleteAdmin(String jsonData) throws JsonProcessingException {
        ID userPersonalData = objectMapper.readValue(jsonData, ID.class);
       String id = userPersonalData.getId();
       UserPersonalData userData = userPersonalDataRepos.findById(id).get();
       Administration administration = administrationRepos.findByUserPersonalData(userData);
       administrationRepos.delete(administration);
        UserAuthData userAuthData = userData.getUserAuthData();
        Role role_teacher = roleRepository.findByName(ERole.ROLE_TEACHER).get();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role_teacher);
        userAuthData.setRoles(roleSet);
        userRepository.save(userAuthData);
       messageService.saveMessage(Collections.singletonList(userData),"Ваш статус адміністратора видалено!!!");
    }

    public List<Administration> getAllAdminsBySchool(School school) {
        return administrationRepos.findByUserPersonalDataSchool(school);
    }
}
