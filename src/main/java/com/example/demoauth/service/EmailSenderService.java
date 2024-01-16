package com.example.demoauth.service;

import com.example.demoauth.models.UserAuthData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender emailSender) {
        this.mailSender = emailSender;
    }


    public void sendVerificationEmail(UserAuthData userAuthData) throws MessagingException, UnsupportedEncodingException {
        String subject = "Будь ласка підтвердіть адресу поштової скриньки";
        String senderName = "Smart School";
        String mailContent = "<p> Вітаємо " + userAuthData.getUsername() + ", </p>";
        mailContent += "<p> Будь ласка перейдіть за наступним посиланням для підтвердження реєстрації: </p>";

        String verifyURL = "http://localhost:8080" + "/verify?code=" + userAuthData.getVerificationCode();
        mailContent += "<h3><a href=\"" + verifyURL + "\">ПОСИЛАННЯ</a></h3>";
        mailContent += "<p>Дякуємо за співпрацю.<br>Smart School</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("caresharelv@gmail.com", senderName);
        helper.setTo(userAuthData.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        Properties props = new Properties();
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mailSender.send(message);

    }

}
