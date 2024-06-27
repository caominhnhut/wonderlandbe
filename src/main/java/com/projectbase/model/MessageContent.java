package com.projectbase.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageContent{

    private String from;
    private String to;
    private String subject;
    private String content;
}
