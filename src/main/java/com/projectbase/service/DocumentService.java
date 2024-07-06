package com.projectbase.service;

import java.util.List;

import com.projectbase.model.Document;

public interface DocumentService{

    Long storeDocument(Document document);

    void updateDocument(Document document);

    Document getDocumentByName(String filename);

    List<Document> getDocumentsByIds(List<Long> ids);
}
