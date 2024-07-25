package com.projectbase.model;

import java.time.LocalDateTime;

import com.projectbase.factory.EntityStatus;

import lombok.Data;

@Data
public class CustomBaseModel{

    protected Long id;
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;
}
