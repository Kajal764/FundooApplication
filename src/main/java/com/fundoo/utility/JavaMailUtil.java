package com.fundoo.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class JavaMailUtil {

    @Autowired
    JavaMailSender javaMailSender;


    public SimpleMailMessage sendMail(String email, String jwtToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        String link="http://localhost:8080/fundoo/verifyUser?token=";
        message.setTo(email);
        message.setSubject("Account verification mail");
        message.setText("Registration Successful to activate your account click on this link   "+(link+jwtToken));
        javaMailSender.send(message);
        return message;
    }

}
