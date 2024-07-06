package com.projectbase.service;

import java.util.List;

import com.projectbase.model.Category;

public interface CategoryService{

    Long create(Category category);

    List<Category> findAll();

    void update(Category category);
}
