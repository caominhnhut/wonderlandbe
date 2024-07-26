package com.projectbase.service;

import com.projectbase.model.ShoppingCart;

public interface ShoppingService{

    ShoppingCart createShoppingSession(Long userId);

    ShoppingCart findById(Long cardId);

    Boolean deleteById(Long cardId);
}
