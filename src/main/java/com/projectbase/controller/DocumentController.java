package com.projectbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.projectbase.dto.ResponseDto;
import com.projectbase.model.Document;
import com.projectbase.service.DocumentService;

@RestController
@RequestMapping(value ="/documents")
public class DocumentController{

    @Autowired
    private DocumentService documentService;

    @PutMapping(value = "/single-upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto<Long>> uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam( name = "document-name", required = false) String documentName){

        Document document = Document.builder()
                .multipartFile(file)
                .documentName(documentName)
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
//    @GetMapping(value = "/no-auth/document/file-content")
//    public ResponseEntity downloadFile(@RequestParam("filename") String filename) {
//
//        byte[] content;
//
//        try {
//            content = documentService.loadFileByName(filename);
//        } catch (IOException e) {
//            return new ResponseEntity<>(String.format("File not found: [%s]", e.getMessage()), HttpStatus.NOT_FOUND);
//        }
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(new ByteArrayResource(content));
//    }
}
