package com.projectbase.validator;

import static com.projectbase.factory.Utility.validatePassword;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.projectbase.dto.ChangePasswordRequestDto;
import com.projectbase.dto.Error;
import com.projectbase.dto.ErrorCodes;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.ValidationType;

import liquibase.util.StringUtil;

@Component
public class ChangePasswordValidator implements Validator<ChangePasswordRequestDto>{

    @Override
    public ValidationType getType(){
        return ValidationType.USER_CHANGE_PASSWORD;
    }

    @Override
    public void validate(ChangePasswordRequestDto changePasswordRequestDto){

        if(StringUtil.isEmpty(changePasswordRequestDto.getOldPassword()) || StringUtil.isEmpty(changePasswordRequestDto.getNewPassword())){
            throw new ValidationException(new Error(ErrorCodes.CHANGE_PASSWORD_INVALID));
        }

        if(!validatePassword(changePasswordRequestDto.getNewPassword())){
            throw new ValidationException(new Error(ErrorCodes.CREATE_USER_INVALID_PASSWORD));
        }
    }
}
