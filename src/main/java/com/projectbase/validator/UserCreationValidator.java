package com.projectbase.validator;

import static com.projectbase.factory.Utility.validateEmail;
import static com.projectbase.factory.Utility.validatePassword;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.projectbase.dto.Error;
import com.projectbase.dto.ErrorCodes;
import com.projectbase.dto.UserRequest;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.Utility;
import com.projectbase.factory.ValidationType;

import liquibase.util.StringUtil;

@Component
public class UserCreationValidator implements Validator<UserRequest>{

    @Override
    public ValidationType getType(){
        return ValidationType.USER_CREATION;
    }

    @Override
    public void validate(UserRequest userRequest){

        checkRequiredData(userRequest);

        checkLogic(userRequest);
    }

    private void checkRequiredData(UserRequest userRequest){

        if(StringUtil.isEmpty(userRequest.getEmail())){
            throw new ValidationException(new Error(ErrorCodes.CREATE_USER_INVALID_EMAIL));
        }

        if(StringUtil.isEmpty(userRequest.getPassword())){
            throw new ValidationException(new Error(ErrorCodes.CREATE_USER_MISSING_PASSWORD));
        }

        if(StringUtil.isEmpty(userRequest.getConfirmedPassword())){
            throw new ValidationException(new Error(ErrorCodes.CREATE_USER_MISSING_CONFIRMED_PASSWORD));
        }
    }

    private void checkLogic(UserRequest userRequest){

        if(!validateEmail(userRequest.getEmail())){
            throw new ValidationException(new Error(ErrorCodes.CREATE_USER_INVALID_EMAIL));
        }

        if(!validatePassword(userRequest.getPassword())){
            throw new ValidationException(new Error(ErrorCodes.CREATE_USER_INVALID_PASSWORD));
        }

        if(!userRequest.getPassword().equals(userRequest.getConfirmedPassword())){
            throw new ValidationException(new Error(ErrorCodes.CREATE_USER_PASSWORDS_NOT_MATCH));
        }
    }
}
