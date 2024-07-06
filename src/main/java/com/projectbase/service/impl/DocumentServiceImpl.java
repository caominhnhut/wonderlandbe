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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.projectbase.entity.DocumentEntity;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.EntityStatus;
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

    //Ex:  //download.url = /api/no-auth/documents/download?filename=uniqueFilename
    @Value("${download.url}")
    private String downloadUrl;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Long storeDocument(Document document){

        MultipartFile file = document.getMultipartFile();

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        log.info("Uploading file has name [{}]", filename);

        String uniqueFilename = createUniqueName(filename);
        Path fileLocation = storeDocumentToDirectory(file, uniqueFilename, DIRECTORY_PRODUCT_IMAGES_NAME);

        DocumentEntity documentEntity = DocumentEntity.builder()
                .documentName(document.getDocumentName())
                .fileName(uniqueFilename)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .fileUrl(downloadUrl.concat(uniqueFilename).replace("%3F", "?"))
                .fileLocation(fileLocation.toString())
                .build();

        documentEntity.setStatus(EntityStatus.ACTIVATED);
        return documentRepository.save(documentEntity).getId();
    }

    @Override
    public void updateDocument(Document document){

    }

    @Override
    public Document getDocumentByName(String filename){
        return null;
    }

    @Override
    public List<Document> getDocumentsByIds(List<Long> ids){
        return null;
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
}
