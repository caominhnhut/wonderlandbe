package com.projectbase.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.projectbase.entity.CategoryEntity;
import com.projectbase.entity.ProductMetadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Product{

    private Long id;

    private String name;

    private String description;

    private String formality;

    private String sizes;

    private String colors;

    private int amount;

    private BigDecimal costPrice;

    private BigDecimal price;

    private ProductMetadata metadata;

    private Set<Long> categoryIds;

}
