package com.projectbase.service.messaging;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectbase.factory.MessageSendingType;
import com.projectbase.model.MessageContent;

@Service
public class MessageService{

    @Autowired
    List<MessageSendingService> messagingServices;

    public void sendMessage(MessageContent content, MessageSendingType sendingType){
        messagingServices.stream().filter(s ->s.setMessageSendingType() == sendingType).findFirst().orElseThrow(() -> new IllegalStateException("Service not found")).send(content);
    }
}
