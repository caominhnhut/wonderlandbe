package com.projectbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error{

    private String status;

    private String title;

    private String detail;

    public Error(ErrorCodes errorCode) {
        this.status = errorCode.getStatus();
        this.title = errorCode.getTitle();
        this.detail = errorCode.getDetail();
    }
}
