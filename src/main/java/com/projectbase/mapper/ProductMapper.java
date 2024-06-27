
package com.projectbase.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.projectbase.dto.CategoryDto;
import com.projectbase.dto.ProductDto;
import com.projectbase.entity.CategoryEntity;
import com.projectbase.entity.ProductEntity;
import com.projectbase.entity.RoleEntity;
import com.projectbase.model.Category;
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

    default Set<Long> getCategoriesFromProductEntity(Set<CategoryEntity> categoryEntities) {
        return categoryEntities.stream().map(CategoryEntity::getId).collect(Collectors.toSet());
    }
}
