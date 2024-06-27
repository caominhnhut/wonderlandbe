package com.projectbase.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectbase.dto.CategoryDto;
import com.projectbase.dto.ResponseDto;
import com.projectbase.factory.ValidationType;
import com.projectbase.mapper.CategoryMapper;
import com.projectbase.model.Category;
import com.projectbase.service.CategoryService;
import com.projectbase.validator.ValidatorProvider;

@RestController
@RequestMapping(value ="/categories")
public class CategoryController{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ValidatorProvider validatorProvider;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createCategory(@RequestBody CategoryDto categoryDto) {

        validatorProvider.execute(ValidationType.CATEGORY_CREATION, categoryDto);

        Category category = categoryMapper.toCategory(categoryDto);
        Long categoryId = categoryService.create(category);

        return ResponseEntity.ok(ResponseDto.response(categoryId));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<CategoryDto>>> getAll() {

        List<Category> categories = categoryService.findAll();

        List<CategoryDto> categoryDtos = categories.stream().map(categoryMapper::fromCategory).collect(Collectors.toList());

        return ResponseEntity.ok(ResponseDto.response(categoryDtos));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Boolean>> update(@RequestBody CategoryDto categoryDto, @PathVariable("id") Long categoryId) {

        categoryDto.setId(categoryId);
        Category category = categoryMapper.toCategory(categoryDto);
        categoryService.update(category);

        return ResponseEntity.ok(ResponseDto.response(Boolean.TRUE));
    }
}
