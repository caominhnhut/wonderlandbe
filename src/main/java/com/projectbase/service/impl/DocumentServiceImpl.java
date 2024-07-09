package com.projectbase.service.impl;

import static com.projectbase.factory.Utility.createUniqueName;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.projectbase.entity.DocumentEntity;
import com.projectbase.exception.ApplicationException;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.DocumentType;
import com.projectbase.factory.EntityStatus;
import com.projectbase.mapper.DocumentMapper;
import com.projectbase.model.Document;
import com.projectbase.repository.DocumentRepository;
import com.projectbase.service.DocumentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService{

    private static final String DIRECTORY_PRODUCT_IMAGES_NAME = "product-photos";

    //Ex: /Users/nhut.nguyen/Documents/personal/myhub/wonderlandbe/assets/
    @Value("${root.directory}")
    private String rootDirectory;

    //Ex:  //download.url = /api/%s/documents/%s/download?filename=uniqueFilename
    @Value("${download.url}")
    private String downloadUrl;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public Long storeDocument(Document document){

        MultipartFile file = document.getMultipartFiles().stream().findFirst().orElseThrow(()->new ValidationException("File not provided"));

        return documentRepository.save(buildDocumentEntity(file, document.getDocumentName(), document.getDocumentType())).getId();
    }

    @Override
    public List<Long> storeDocumentsPerFiles(Document document){

        List<DocumentEntity> documentEntities = document
                .getMultipartFiles()
                .stream()
                .map(file -> buildDocumentEntity(file, document.getDocumentName(), document.getDocumentType()))
                .collect(Collectors.toList());

        return documentRepository.saveAll(documentEntities).stream().map(DocumentEntity::getId).collect(Collectors.toList());
    }

    @Override
    public void updateDocument(Document document){
    }

    @Override
    public Document getDocumentByFilename(String filename){
        DocumentEntity documentEntity = documentRepository.findByFilenameAndStatus(filename, EntityStatus.ACTIVATED);
        if(documentEntity == null){
            throw new ApplicationException(String.format("[%s] not found", filename));
        }

        Document document = documentMapper.fromDocumentEntity(documentEntity);

        try{
           byte[] fileContent = Files.readAllBytes(Paths.get(documentEntity.getFileLocation()));
           document.setFileContent(fileContent);
        }catch(IOException e){
            throw new ApplicationException(String.format("Error when loading document [%s]", filename));
        }

        return document;
    }

    @Override
    public List<Document> getDocumentsByIds(List<Long> ids){
        return null;
    }

    @Override
    public byte[] getFileByName(String filename){
        DocumentEntity documentEntity = documentRepository.findByFilenameAndStatus(filename, EntityStatus.ACTIVATED);
        if(documentEntity == null){
            throw new ApplicationException(String.format("[%s] not found", filename));
        }

        try{
            return Files.readAllBytes(Paths.get(documentEntity.getFileLocation()));
        }catch(IOException e){
            throw new ApplicationException(String.format("Error when loading file [%s]", filename));
        }
    }

    private Path storeDocumentToDirectory(MultipartFile file, String filename, String directoryName) {

        try (InputStream inputStream = file.getInputStream()) {
            Path path = Paths.get(String.format("%s/%s/%s", rootDirectory, directoryName, filename));
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (IOException e) {
            throw new ValidationException(String.format("Error while saving document: [%s]", e.getMessage()));
        }
    }

    private String generateDocumentUrl(String documentName, DocumentType documentType){
        return String.format(downloadUrl.replace("%3F", "?"), documentType.getAuthentication(), documentType.toString().toLowerCase())
                .concat(documentName);
    }

    private DocumentEntity buildDocumentEntity(MultipartFile file, String documentName, DocumentType documentType){

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        log.info("Generating a document with filename [{}]", filename);

        String uniqueFilename = createUniqueName(filename);
        Path fileLocation = storeDocumentToDirectory(file, uniqueFilename, DIRECTORY_PRODUCT_IMAGES_NAME);

        DocumentEntity documentEntity = DocumentEntity.builder()
                .documentName(documentName)
                .documentType(documentType)
                .fileName(uniqueFilename)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .fileUrl(generateDocumentUrl(uniqueFilename, documentType))
                .fileLocation(fileLocation.toString())
                .build();

        documentEntity.setStatus(EntityStatus.ACTIVATED);

        return documentEntity;
    }
}
