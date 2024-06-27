package com.projectbase.exception;

import static com.projectbase.dto.ErrorCodes.INTERNAL_SERVER_ERROR;

import java.util.List;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.projectbase.dto.Error;
import com.projectbase.dto.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(value = { InvalidDataAccessApiUsageException.class })
    protected ResponseEntity<ResponseDto<String>> handleInvalidDataAPI(InvalidDataAccessApiUsageException e, WebRequest webRequest){
        log.error("Invalid data error: {}", e.getMessage());

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setErrors(List.of(new Error(INTERNAL_SERVER_ERROR)));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(value = { AuthenticationException.class })
    protected ResponseEntity<ResponseDto<String>> handleAuthenticationFailed(AuthenticationException e, WebRequest webRequest){
        log.error("Authentication error: {}", e.getMessage());

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setErrors(List.of(Error.builder().detail(e.getMessage()).build()));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }

    @ExceptionHandler(value = { ValidationException.class })
    protected ResponseEntity<ResponseDto<String>> handleValidationException(ValidationException e, WebRequest webRequest){
        log.error("ValidationException error: {}", e.getError().getDetail());

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setErrors(List.of(e.getError()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(value = { ApplicationException.class })
    protected ResponseEntity<ResponseDto<String>> handleApplicationException(ApplicationException e, WebRequest webRequest){
        log.error("ApplicationException error: {}", e.getMessage());

        ResponseDto<String> responseDto = new ResponseDto<>();
        responseDto.setErrors(List.of(Error.builder().detail(e.getMessage()).build()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

}
