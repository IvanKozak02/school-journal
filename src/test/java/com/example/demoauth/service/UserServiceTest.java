package com.example.demoauth.service;

import com.example.demoauth.models.UserAuthData;
import com.example.demoauth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;



    @Test
    void verify() {
        UserAuthData userAuthData = new UserAuthData();
        userAuthData.setId(20L);
        Mockito.when(userRepository.findByVerificationCode(ArgumentMatchers.anyString())).thenReturn(userAuthData);
        boolean isUserEnabled = userService.verify(ArgumentMatchers.anyString());
        assertTrue(isUserEnabled);
    }

    @Test
    void checkIfUserHasProfile() {
    }
}