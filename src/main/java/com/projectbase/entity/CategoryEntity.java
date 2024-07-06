package com.projectbase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "category")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class CategoryEntity extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
