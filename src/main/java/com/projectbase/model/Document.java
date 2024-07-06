package com.projectbase.model;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.projectbase.factory.DocumentType;
import com.projectbase.factory.EntityStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Document{

    private Long id;

    private String documentName;

    private String fileName;

    private String fileType;

    private String fileUrl;

    private Long fileSize;

    private byte[] fileContent;

    private MultipartFile multipartFile;

    private EntityStatus status;

    private DocumentType documentType;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
