package com.projectbase.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectbase.entity.CategoryEntity;
import com.projectbase.entity.ProductEntity;
import com.projectbase.entity.ProductMetadata;
import com.projectbase.exception.ApplicationException;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.DocumentType;
import com.projectbase.factory.EntityStatus;
import com.projectbase.mapper.ProductMapper;
import com.projectbase.model.Document;
import com.projectbase.model.Product;
import com.projectbase.model.ProductImagesToUpload;
import com.projectbase.repository.CategoryRepository;
import com.projectbase.repository.ProductRepository;
import com.projectbase.service.DocumentService;
import com.projectbase.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DocumentService documentService;

    @Override
    @Transactional
    public Long create(Product product){

        Set<CategoryEntity> categoryEntities = categoryRepository.findByIds(product.getCategoryIds());
        if(null == categoryEntities || (categoryEntities.size() != product.getCategoryIds().size())){
            throw new ApplicationException("Categories not found");
        }

        ProductEntity productEntity = productMapper.toProductEntity(product);
        productEntity.setStatus(EntityStatus.ACTIVATED);
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
            productMapper.updateFromProduct(product, entity);
            productRepository.save(entity);
        }else {
            throw new ApplicationException("Product not found");
        }
    }

    @Override
    @Transactional
    public void storeProductImages(ProductImagesToUpload productImages){

        Optional<ProductEntity> entityOpt = productRepository.findById(productImages.getProductId());
        if(!entityOpt.isPresent()){
            throw new ValidationException(String.format("Product not found for %s", productImages.getProductId()));
        }

        Document mainDocument = Document.builder()
                .multipartFiles(List.of(productImages.getMainImage()))
                .documentName("image-for-product-".concat(String.valueOf(productImages.getProductId())))
                .documentType(DocumentType.PRODUCT_IMAGE)
                .build();
        Document mainImage = documentService.storeDocument(mainDocument);

        Document extraDocument = Document.builder()
                .multipartFiles(productImages.getExtraImages())
                .documentName("extra-images-for-product-"+productImages.getProductId())
                .documentType(DocumentType.PRODUCT_IMAGE)
                .build();
        List<Document> extraImages = documentService.storeDocumentsPerFiles(extraDocument);

        Map<String, String> images = new HashMap<>();
        images.put("mainImage", mainImage.getFileUrl());

        for(int i=0; i<extraImages.size(); i++){
            images.put("extraImage-"+i, extraImages.get(i).getFileUrl());
        }

        ProductEntity productEntity = entityOpt.get();
        productEntity.setMetadata(ProductMetadata.builder().images(images).build());

        productRepository.save(productEntity);
    }
}
