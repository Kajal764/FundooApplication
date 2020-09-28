package com.fundoo.user.service;

import com.fundoo.user.model.MailData;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    public String send(MailData mailData) {
        rabbitTemplate.convertAndSend(exchange, routingKey, mailData);
        return "Added in Rabbit Queue";
    }

}
