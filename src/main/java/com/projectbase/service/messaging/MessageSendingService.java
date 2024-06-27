package com.projectbase.service.messaging;

import com.projectbase.factory.MessageSendingType;
import com.projectbase.model.MessageContent;

public interface MessageSendingService{

    MessageSendingType setMessageSendingType();

    void send(MessageContent content);

}
