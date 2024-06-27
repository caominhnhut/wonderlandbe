package com.projectbase.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectbase.entity.CategoryEntity;
import com.projectbase.exception.ApplicationException;
import com.projectbase.mapper.CategoryMapper;
import com.projectbase.model.Category;
import com.projectbase.repository.CategoryRepository;
import com.projectbase.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    @Override
    public Long create(Category category){
        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(category);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryEntity.getId();
    }

    @Override
    public List<Category> findAll(){
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        return categoryEntities.stream().map(categoryMapper::fromCategoryEntity).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void update(Category category){
        Optional<CategoryEntity> entityOpt = categoryRepository.findById(category.getId());
        if(entityOpt.isPresent()){
            CategoryEntity entity = entityOpt.get();
            entity.setName(category.getName());
            entity.setDescription(category.getDescription());
            categoryRepository.save(entity);
        }else {
            throw new ApplicationException("Category not found");
        }
    }
}
