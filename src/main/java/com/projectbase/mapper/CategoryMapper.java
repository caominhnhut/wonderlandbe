package com.projectbase.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.projectbase.dto.CategoryDto;
import com.projectbase.entity.CategoryEntity;
import com.projectbase.model.Category;

@Component
@Mapper(componentModel = "spring")
public interface CategoryMapper{

    CategoryEntity toCategoryEntity(Category category);

    Category toCategory(CategoryDto categoryDto);

    CategoryDto fromCategory(Category category);

    Category fromCategoryEntity(CategoryEntity categoryEntity);
}
