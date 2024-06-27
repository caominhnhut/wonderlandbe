package com.projectbase.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class ResponseDto<T>{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Error> errors;

    public static <T> ResponseDto<T> response(T data) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setData(data);
        return responseDto;
    }

    public static <T> ResponseDto<T> errorResponse(List<Error> errors) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setErrors(errors);
        return responseDto;
    }
}
