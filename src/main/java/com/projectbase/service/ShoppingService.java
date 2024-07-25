package com.projectbase.service;

import com.projectbase.model.ShoppingCart;

public interface ShoppingService{

    ShoppingCart createShoppingSession(Long userId);
}
