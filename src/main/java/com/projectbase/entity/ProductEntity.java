package com.projectbase.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "product")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ProductEntity extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "formality")
    private String formality;

    @Column(name = "sizes")
    private String sizes;

    @Column(name = "colors")
    private String colors;

    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "images")
    private String images;

    @Column(name = "amount")
    private int amount;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "categories_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories;
}
