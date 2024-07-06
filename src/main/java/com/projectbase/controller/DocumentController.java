package com.projectbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
                .multipartFile(file)
                .documentName(documentName)
                .documentType(documentType)
                .build();

        Long documentId = documentService.storeDocument(document);

        return ResponseEntity.ok(ResponseDto.response(documentId));
    }

//    @PostMapping(value = "/document/upload-files")
//    @PreAuthorize("hasRole('ROLE_GUESS') or hasRole('ROLE_STUDENT') or hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<DocumentEntity>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//
//                List<DocumentEntity> fileEntities = Arrays.asList(files).stream().map(file -> {
//                    try {
//                        return documentService.storeImage(file);
//                    } catch (ValidationException e) {
//                        return new ResponseEntity<>(String.format("File not found: [%s]", e.getMessage()), HttpStatus.NOT_FOUND);
//                    }
//                }).collect(Collectors.toList());
//
//                return new ResponseEntity<>(fileEntities, HttpStatus.CREATED);
//        return null;
//    }
//
    @GetMapping(value = "/no-auth/documents/{document-type}/download")
    public ResponseEntity<ResponseDto<Document>> downloadDocument(@PathVariable("document-type") String documentType, @RequestParam("filename") String filename) {

        validatorProvider.execute(ValidationType.DOCUMENT_DOWNLOAD, DocumentDownloadRequestDto
                .builder()
                .fileName(filename)
                .documentType(documentType)
                .build()
        );

        Document document = documentService.getDocumentByFilename(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", filename))
                .body(ResponseDto.response(document));
    }
}
