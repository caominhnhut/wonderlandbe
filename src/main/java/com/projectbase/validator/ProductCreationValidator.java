package com.projectbase.validator;

import org.springframework.stereotype.Component;

import com.projectbase.dto.Error;
import com.projectbase.dto.ErrorCodes;
import com.projectbase.dto.ProductDto;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.ValidationType;

import liquibase.util.StringUtil;

@Component
public class ProductCreationValidator implements Validator<ProductDto>{

    @Override
    public ValidationType getType(){
        return ValidationType.PRODUCT_CREATION;
    }

    @Override
    public void validate(ProductDto productDto){

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
