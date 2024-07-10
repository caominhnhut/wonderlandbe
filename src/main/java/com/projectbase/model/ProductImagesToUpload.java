package com.projectbase.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductImagesToUpload{

    private MultipartFile mainImage;

    private List<MultipartFile> extraImages;

    private Long productId;
}
