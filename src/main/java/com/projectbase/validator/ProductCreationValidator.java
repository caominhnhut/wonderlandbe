package com.projectbase.validator;

import org.springframework.stereotype.Component;

import com.projectbase.dto.CreateProductRequestDto;
import com.projectbase.dto.Error;
import com.projectbase.dto.ErrorCodes;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.ValidationType;

import liquibase.util.StringUtil;

@Component
public class ProductCreationValidator implements Validator<CreateProductRequestDto>{

    @Override
    public ValidationType getType(){
        return ValidationType.PRODUCT_CREATION;
    }

    @Override
    public void validate(CreateProductRequestDto productDto){

        if(StringUtil.isEmpty(productDto.getName())){
            throw new ValidationException(new Error(ErrorCodes.CREATE_PRODUCT_MISSING_NAME));
        }

        if(null == productDto.getCostPrice()){
            throw new ValidationException(new Error(ErrorCodes.CREATE_PRODUCT_MISSING_COST));
        }

        if(null == productDto.getPrice()){
            throw new ValidationException(new Error(ErrorCodes.CREATE_PRODUCT_MISSING_PRICE));
        }
    }
}
