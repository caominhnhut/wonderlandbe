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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Product{

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
