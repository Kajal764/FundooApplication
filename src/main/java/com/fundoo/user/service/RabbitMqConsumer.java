package com.fundoo.user.service;//package com.fundoo.user.service;

import com.fundoo.user.model.MailData;
import com.fundoo.user.utility.JavaMailUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class RabbitMqConsumer {

    @Autowired
    JavaMailUtil javaMailUtil;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(MailData mailData) throws MessagingException {
        javaMailUtil.sendMail(mailData);
    }
}
