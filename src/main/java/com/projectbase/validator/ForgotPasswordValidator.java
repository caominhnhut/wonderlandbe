package com.projectbase.validator;

import org.springframework.stereotype.Component;

import com.projectbase.dto.Error;
import com.projectbase.dto.ErrorCodes;
import com.projectbase.dto.ForgotPasswordRequestDto;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.ValidationType;

import liquibase.util.StringUtil;

@Component
public class ForgotPasswordValidator implements Validator<ForgotPasswordRequestDto>{

    @Override
    public ValidationType getType(){
        return ValidationType.FORGOT_PASSWORD;
    }

    @Override
    public void validate(ForgotPasswordRequestDto forgotPasswordRequestDto){

        if(StringUtil.isEmpty(forgotPasswordRequestDto.getEmailAddress())){
            throw new ValidationException(new Error(ErrorCodes.FORGOT_PASSWORD_MISSING_EMAIL));
        }
    }
}
