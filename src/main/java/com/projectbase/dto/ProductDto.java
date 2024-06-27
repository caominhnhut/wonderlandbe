package com.projectbase.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductDto{

    private Long id;

    private String name;

    private String description;

    private String formality;

    private String sizes;

    private String colors;

    private String images;

    private int amount;

    private BigDecimal costPrice;

    private BigDecimal price;

    private Set<Long> categoryIds;

}
