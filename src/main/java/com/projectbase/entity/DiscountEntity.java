package com.projectbase.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "discount")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class DiscountEntity extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String lastName;

    @Column(name = "percentage")
    private BigDecimal percentage;

    @Column(name = "fixed_price")
    private BigDecimal fixedPrice;

    @OneToMany(mappedBy = "discount")
    private Set<ProductEntity> products;

}
