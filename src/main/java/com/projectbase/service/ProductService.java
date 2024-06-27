package com.projectbase.service;

import java.util.List;

import com.projectbase.model.Category;
import com.projectbase.model.Product;

public interface ProductService{

    Long create(Product product);

    List<Product> findAll();

    Product findById(Long id);

    void update(Product product);
}
