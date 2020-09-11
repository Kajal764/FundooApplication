package com.fundoo.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JavaMailUtilTest {

    @Autowired
    JavaMailSender javaMailSender;

//    @Test
//    void name() throws MessagingException {
//
//        SimpleMailMessage simpleMailMessage = new RegistrationService().javaMailUtil.sendMail("kajalw1998@gmail.com");
//        when(javaMailSender.send(new SimpleMailMessage())).thenReturn(void);
//        Assert.assertEquals(simpleMailMessage.getSubject(),"Account verification mail");
//
//    }
}
