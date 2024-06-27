package com.projectbase.validator;

import org.springframework.stereotype.Component;

import com.projectbase.dto.CategoryDto;
import com.projectbase.dto.Error;
import com.projectbase.dto.ErrorCodes;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.ValidationType;

import liquibase.util.StringUtil;

@Component
public class CreateCategoryValidator implements Validator<CategoryDto>{

    @Override
    public ValidationType getType(){
        return ValidationType.CATEGORY_CREATION;
    }

    @Override
    public void validate(CategoryDto categoryDto){
        if(StringUtil.isEmpty(categoryDto.getName())){
            throw new ValidationException(new Error(ErrorCodes.CREATE_CATEGORY_MISSING_NAME));
        }
    }
}
