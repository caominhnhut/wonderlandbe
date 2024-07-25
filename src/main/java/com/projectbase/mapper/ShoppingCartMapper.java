package com.projectbase.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projectbase.entity.CartItemEntity;
import com.projectbase.entity.CategoryEntity;
import com.projectbase.entity.ProductEntity;
import com.projectbase.entity.ShoppingSessionEntity;
import com.projectbase.model.CartItem;
import com.projectbase.model.Product;
import com.projectbase.model.ShoppingCart;

@Component
@Mapper(componentModel = "spring")
public interface ShoppingCartMapper{

    @Mapping(target = "customerId", source = "shoppingSession.customer.id")
    @Mapping(target = "cartItems", expression = "java(getCartItemFromCartEntity(shoppingSession.getCartItems()))")
    ShoppingCart fromShoppingCartEntity(ShoppingSessionEntity shoppingSession);

    default List<CartItem> getCartItemFromCartEntity(Set<CartItemEntity> cartItemEntities) {
        if(cartItemEntities == null || cartItemEntities.isEmpty()){
            return Collections.emptyList();
        }

        return cartItemEntities.stream().map(e -> CartItem.builder()
                .quantity(e.getQuantity())
                .product(fromProductEntity(e.getProduct()))
                .build()).collect(Collectors.toList());
    }

    default Product fromProductEntity(ProductEntity entity){
        if(entity == null){
            return null;
        }

        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }
}
