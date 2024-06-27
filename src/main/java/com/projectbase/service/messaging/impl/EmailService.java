package com.projectbase.service.messaging.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.projectbase.factory.MessageSendingType;
import com.projectbase.model.MessageContent;
import com.projectbase.service.messaging.MessageSendingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService implements MessageSendingService{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public MessageSendingType setMessageSendingType(){
        return MessageSendingType.EMAIL;
    }

    @Override
    public void send(MessageContent content){
        log.info("Sending email: {}", content);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(content.getFrom());
        simpleMailMessage.setTo(content.getTo());
        simpleMailMessage.setSubject(content.getSubject());
        simpleMailMessage.setText(content.getContent());

        mailSender.send(simpleMailMessage);
    }
}
