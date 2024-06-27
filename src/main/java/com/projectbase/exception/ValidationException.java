package com.projectbase.exception;

import com.projectbase.dto.Error;

import lombok.Data;

@Data
public class ValidationException extends RuntimeException{

    private Error error;

    public ValidationException(String message){
        super(message);
    }

    public ValidationException(Error error){
        this.error = error;
    }
}
