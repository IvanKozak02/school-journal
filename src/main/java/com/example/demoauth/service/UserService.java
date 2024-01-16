package com.example.demoauth.service;

import com.example.demoauth.models.UserAuthData;
import com.example.demoauth.models.UserPersonalData;
import com.example.demoauth.repository.UserPersonalDataRepos;
import com.example.demoauth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    UserPersonalDataRepos userPersonalDataRepos;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserPersonalDataRepos userPersonalDataRepos) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userPersonalDataRepos = userPersonalDataRepos;
    }

    public boolean verify(String verificationCode){
        UserAuthData userAuthData = userRepository.findByVerificationCode(verificationCode);
        if (userAuthData == null || userAuthData.isEnabled()){
            return false;
        }
        else {
            userRepository.enable(userAuthData.getId());
            return true;
        }
    }

    public boolean checkIfUserEnable(String email, String password){
        UserAuthData userAuthData = null;
        if (userRepository.existsByEmail(email) && passwordEncoder.matches(password, userRepository.findByEmail(email).get().getPassword())) {
            userAuthData = userRepository.findByEmail(email).get();
        }
        return userAuthData != null && userAuthData.isEnabled();
    }

    public boolean checkIfUserHasProfile(String email){
        UserAuthData userAuthData = userRepository.findByEmail(email).get();
        return userAuthData.isHasProfile();
    }

    public boolean checkIfUserExists(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean checkIfUserPasswordIsOk(String email, String password){
        return passwordEncoder.matches(password,userRepository.findByEmail(email).get().getPassword());
    }


    public String getUserProfileId(String email){
        UserPersonalData userPersonalData = userPersonalDataRepos.findUserPersonalDataByEmail(email);
        return userPersonalData.getId();
    }

    public UserAuthData getUserByEmail(String email){
       return userRepository.findByEmail(email).get();
    }




}

