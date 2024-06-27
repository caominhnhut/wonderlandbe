package com.projectbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectbase.entity.CategoryEntity;
import com.projectbase.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

}
