package com.fundoo.user.utility;

import com.fundoo.user.model.MailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class JavaMailUtil {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(MailData mailData) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(mailData.getEmail());
        helper.setSubject(mailData.getSubject());
        helper.setText(mailData.getMessage(), true);
        javaMailSender.send(message);
    }

    public SimpleMailMessage resetPwMail(String email, String jwtToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        String link = "http://localhost:4200/update-password";
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link : " + (link + ";token=" + jwtToken));
        javaMailSender.send(message);
        return message;
    }

    public String sendCollaborationInvite(String email, String subject, String template) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(template, true);
        javaMailSender.send(message);
        return "Collaborate user successfully";
    }

}
