package com.projectbase.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.projectbase.dto.DocumentDownloadRequestDto;
import com.projectbase.dto.ResponseDto;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.DocumentType;
import com.projectbase.factory.ValidationType;
import com.projectbase.model.Document;
import com.projectbase.service.DocumentService;
import com.projectbase.validator.ValidatorProvider;

@RestController
@RequestMapping(value ="/")
public class DocumentController{

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ValidatorProvider validatorProvider;

    @PutMapping(value = "documents/single-upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto<Long>> uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam( name = "document-name", required = false) String documentName, @RequestParam("document-type") DocumentType documentType){

        if(documentType == null){
            throw new ValidationException("Document type is not provided");
        }

        Document document = Document.builder()
                .multipartFiles(List.of(file))
                .documentName(documentName)
                .documentType(documentType)
                .build();

        Long documentId = documentService.storeDocument(document).getId();

        return ResponseEntity.ok(ResponseDto.response(documentId));
    }

    @GetMapping(value = "/no-auth/documents/{document-type}/download")
    public ResponseEntity downloadFileContent(@PathVariable("document-type") String documentType, @RequestParam("filename") String filename) {

        validatorProvider.execute(ValidationType.DOCUMENT_DOWNLOAD, DocumentDownloadRequestDto
                .builder()
                .fileName(filename)
                .documentType(documentType)
                .build()
        );

        byte[] fileContent = documentService.getFileByName(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", filename))
                .body(new ByteArrayResource(fileContent));
    }

    @GetMapping(value = "documents")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto<Document>> getDocumentByName(@RequestBody DocumentDownloadRequestDto dto) {

        validatorProvider.execute(ValidationType.DOCUMENT_DOWNLOAD, dto);

        Document document = documentService.getDocumentByFilename(dto.getFileName());

        return ResponseEntity.ok(ResponseDto.response(document));
    }

    @PutMapping(value = "/documents/multiple-upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto<List<Long>>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam( name = "document-name", required = false) String documentName, @RequestParam("document-type") DocumentType documentType) {

        if(documentType == null){
            throw new ValidationException("Document type is not provided");
        }

        Document document = Document.builder()
                .multipartFiles(Arrays.asList(files))
                .documentName(documentName)
                .documentType(documentType)
                .build();

        List<Long> documentIds = documentService.storeDocumentsPerFiles(document).stream().map(Document::getId).collect(Collectors.toList());

        return ResponseEntity.ok(ResponseDto.response(documentIds));
    }
}
