package com.projectbase.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectbase.entity.CategoryEntity;
import com.projectbase.entity.ProductEntity;
import com.projectbase.exception.ApplicationException;
import com.projectbase.mapper.ProductMapper;
import com.projectbase.model.Product;
import com.projectbase.repository.CategoryRepository;
import com.projectbase.repository.ProductRepository;
import com.projectbase.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Long create(Product product){

        Set<CategoryEntity> categoryEntities = categoryRepository.findByIds(product.getCategoryIds());
        if(null == categoryEntities || (categoryEntities.size() != product.getCategoryIds().size())){
            throw new ApplicationException("Categories not found");
        }

        ProductEntity productEntity = productMapper.toProductEntity(product);
        productEntity.setCategories(categoryEntities);
        ProductEntity newlyProductEntity = productRepository.save(productEntity);

        return newlyProductEntity.getId();
    }

    @Override
    public List<Product> findAll(){
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream().map(productMapper::fromProductEntity).collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id){
        Optional<ProductEntity> optEntity = productRepository.findById(id);
        if(!optEntity.isPresent()){
            throw new ApplicationException("Product not found");
        }

        return productMapper.fromProductEntity(optEntity.get());
    }

    @Override
    @Transactional
    public void update(Product product){
        Optional<ProductEntity> entityOpt = productRepository.findById(product.getId());
        if(entityOpt.isPresent()){
            ProductEntity entity = entityOpt.get();
            entity.setName(product.getName());
            entity.setDescription(product.getDescription());
            productRepository.save(entity);
        }else {
            throw new ApplicationException("Product not found");
        }
    }
}
