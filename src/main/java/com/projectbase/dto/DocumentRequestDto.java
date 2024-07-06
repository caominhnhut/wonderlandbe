package com.projectbase.dto;

import java.time.LocalDateTime;

import com.projectbase.factory.EntityStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class DocumentDto{

    private Long id;

    private String name;

    private String fileType;

    private String fileUrl;

    private Long fileSize;

    private byte[] fileContent;

    private EntityStatus status;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
