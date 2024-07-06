package com.projectbase.validator;

import org.springframework.stereotype.Component;

import com.projectbase.dto.DocumentDownloadRequestDto;
import com.projectbase.dto.Error;
import com.projectbase.dto.ErrorCodes;
import com.projectbase.dto.ProductDto;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.DocumentType;
import com.projectbase.factory.ValidationType;

import liquibase.util.StringUtil;

@Component
public class DocumentDownloadingValidator implements Validator<DocumentDownloadRequestDto>{

    @Override
    public ValidationType getType(){
        return ValidationType.DOCUMENT_DOWNLOAD;
    }

    @Override
    public void validate(DocumentDownloadRequestDto dto){

        if(StringUtil.isEmpty(dto.getFileName())){
            throw new ValidationException(new Error(ErrorCodes.DOWNLOAD_DOCUMENT_MISSING_FILENAME));
        }

        for(DocumentType documentType: DocumentType.values()){
            if(!documentType.name().equalsIgnoreCase(dto.getDocumentType())){
                throw new ValidationException(new Error(ErrorCodes.DOWNLOAD_DOCUMENT_DOCUMENT_TYPE_NOT_SUPPORTED));
            }
        }
    }
}
