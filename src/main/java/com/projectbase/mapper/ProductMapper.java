
package com.projectbase.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import com.projectbase.dto.ProductDto;
import com.projectbase.entity.CategoryEntity;
import com.projectbase.entity.ProductEntity;
import com.projectbase.model.Product;

@Component
@Mapper(componentModel = "spring")
public interface ProductMapper{

    @Mapping(target = "categories", ignore = true)
    ProductEntity toProductEntity(Product product);

    Product toProduct(ProductDto productDto);

    ProductDto fromProduct(Product product);

    @Mapping(target = "categoryIds", expression = "java(getCategoriesFromProductEntity(productEntity.getCategories()))")
    Product fromProductEntity(ProductEntity productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromProduct(Product product, @MappingTarget ProductEntity productEntity);

    default Set<Long> getCategoriesFromProductEntity(Set<CategoryEntity> categoryEntities) {
        return categoryEntities.stream().map(CategoryEntity::getId).collect(Collectors.toSet());
    }
}
