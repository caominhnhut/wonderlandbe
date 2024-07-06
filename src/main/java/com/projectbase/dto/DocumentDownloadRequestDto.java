package com.projectbase.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentDownloadRequestDto{

    private String fileName;

    private String documentType;
}
