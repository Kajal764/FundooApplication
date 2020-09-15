package com.fundoo.user.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class JavaMailUtil {

    @Autowired
    JavaMailSender javaMailSender;

    public SimpleMailMessage sendMail(String email, String jwtToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        String link = "http://localhost:8080/fundoo/user/verify?token=";
        message.setTo(email);
        message.setSubject("Account verification mail");
        message.setText("Registration Successful to activate your account click on this link   " + (link + jwtToken));
        javaMailSender.send(message);
        return message;
    }

    public SimpleMailMessage resetPwMail(String email, String jwtToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        String link = "http://localhost:8080/fundoo/user/reset_password/";
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link : " + (link + jwtToken));
        javaMailSender.send(message);
        return message;
    }
}
