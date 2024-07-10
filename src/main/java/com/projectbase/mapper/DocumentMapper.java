package com.projectbase.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.projectbase.entity.DocumentEntity;
import com.projectbase.model.Document;

@Component
@Mapper(componentModel = "spring")
public interface DocumentMapper{

    Document fromDocumentEntity(DocumentEntity entity);
}
