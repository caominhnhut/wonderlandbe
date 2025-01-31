package com.projectbase.service;

import com.projectbase.model.CartItem;
import com.projectbase.model.ShoppingCart;

public interface ShoppingService{

    ShoppingCart createShoppingSession(Long userId);

    ShoppingCart findById(Long cartId);

    Boolean deleteById(Long cartId);

    ShoppingCart addItemToCart(Long cartId, CartItem cartItem);
}
