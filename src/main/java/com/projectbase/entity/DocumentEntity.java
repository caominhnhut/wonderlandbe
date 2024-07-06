package com.projectbase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.projectbase.factory.DocumentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "document")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DocumentEntity extends BaseEntity{

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    protected DocumentType documentType;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_location", nullable = false)
    private String fileLocation;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;
}
