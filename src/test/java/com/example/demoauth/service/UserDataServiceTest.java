package com.example.demoauth.service;

import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserDataServiceTest {



    @InjectMocks
    private UserDataService userDataService;

    @Test
    void saveNewProfile() {
        UserPersonalData userPersonalData = new UserPersonalData();
        userPersonalData.setUserName("Abram");
        boolean isUserCreated = userDataService.saveNewProfile(userPersonalData);
        Assertions.assertTrue(isUserCreated);
        Assertions.assertNotNull(userPersonalData.getId());
    }
}