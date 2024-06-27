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
import com.projectbase.dto.ProductDto;
import com.projectbase.dto.ResponseDto;
import com.projectbase.factory.ValidationType;
import com.projectbase.mapper.CategoryMapper;
import com.projectbase.mapper.ProductMapper;
import com.projectbase.model.Category;
import com.projectbase.model.Product;
import com.projectbase.service.CategoryService;
import com.projectbase.service.ProductService;
import com.projectbase.validator.ValidatorProvider;

@RestController
@RequestMapping(value ="/products")
public class ProductController{

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ValidatorProvider validatorProvider;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createProduct(@RequestBody ProductDto productDto) {

        validatorProvider.execute(ValidationType.PRODUCT_CREATION, productDto);

        Product product = productMapper.toProduct(productDto);
        Long productId = productService.create(product);

        return ResponseEntity.ok(ResponseDto.response(productId));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ProductDto>>> getAllProducts() {

        List<Product> products = productService.findAll();

        List<ProductDto> productDtos = products.stream().map(productMapper::fromProduct).collect(Collectors.toList());

        return ResponseEntity.ok(ResponseDto.response(productDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductDto>> getById(@PathVariable("id") Long id) {

        Product product = productService.findById(id);

        return ResponseEntity.ok(ResponseDto.response(productMapper.fromProduct(product)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Boolean>> updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") Long productId) {

        productDto.setId(productId);
        Product product = productMapper.toProduct(productDto);
        productService.update(product);

        return ResponseEntity.ok(ResponseDto.response(Boolean.TRUE));
    }
}
