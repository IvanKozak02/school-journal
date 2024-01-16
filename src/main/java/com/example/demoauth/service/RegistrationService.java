package com.example.demoauth.service;

import com.example.demoauth.models.UserAuthData;
import com.example.demoauth.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class RegistrationService {

    private final UserRepository userRepository;


    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public void registerUser(UserAuthData userAuthData){
        userAuthData.setEnabled(false);
        String randomCode = RandomString.make(64);
        userAuthData.setVerificationCode(randomCode);
        userRepository.save(userAuthData);
    }
}
