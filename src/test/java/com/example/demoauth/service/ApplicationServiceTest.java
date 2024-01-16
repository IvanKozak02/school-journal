package com.example.demoauth.service;

import com.example.demoauth.models.Application;
import com.example.demoauth.models.School;
import com.example.demoauth.models.Subject;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.repository.ApplicationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationService applicationService;



    @Test
    void getAllAppBySchool() {
        School school = getSchool();
        List<Application> applications = getApplication(school);
        Mockito.when(applicationRepository.findAll()).thenReturn(applications);
        List<Application> applicationsBySchool = applicationService.getAllAppBySchool(school);
        Assertions.assertNotNull(applicationsBySchool);
        Assertions.assertEquals(1, applicationsBySchool.size());
        Assertions.assertEquals(applications.get(1), applicationsBySchool.get(0));
    }
    public School getSchool(){
        School school = new School();
        school.setViddil("Lvivskyj");
        Set<Subject> subjectSet = new HashSet<>();
        subjectSet.add(new Subject());
        school.setSubjects(subjectSet);
        school.setCity("Lviv");
        school.setName("632");
        school.setTypeOfSchool("private");
        school.setStreet("Schewska");
        school.setEmail("ssfsd@sdfsdf");
        school.setOblast("Lvivska");
        school.setWebCite("632School");
        school.setContactPhone("123444");
        school.setId(1L);
        return school;
    }

    public List<Application> getApplication(School school){
        Application application = new Application();
        Application application1 = new Application();
        application.setSchool(school);
        application1.setSchool(new School());
//        application.setStatus("new");
//        application.setApproved(false);
//        application.setParentPhone("+4032244");
//        application.setSchool(getSchool());
//        application.setDocURL("dffdsfs");
//        application.setDateOfCreate("02.05.2023");
//        application.setSubjects("Math");
//        application.setUserPersonalData(new UserPersonalData());
//        application.setId(1L);
        return List.of(application1, application);
    }
}