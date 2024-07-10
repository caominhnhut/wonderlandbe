package com.projectbase.service;

import java.util.List;

import com.projectbase.model.Document;

public interface DocumentService{

    Document storeDocument(Document document);

    List<Document> storeDocumentsPerFiles(Document document);

    void updateDocument(Document document);

    Document getDocumentByFilename(String filename);

    List<Document> getDocumentsByIds(List<Long> ids);

    byte[] getFileByName(String filename);
}
