package com.projectbase.model;

import java.time.LocalDateTime;

import com.projectbase.factory.EntityStatus;

import lombok.Data;

@Data
public class BaseModel{

    protected Long id;
    protected EntityStatus status;
    protected String createdBy;
    protected String updatedBy;
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;
}
