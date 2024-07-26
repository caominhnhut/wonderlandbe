package com.projectbase.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectbase.entity.CartItemEntity;
import com.projectbase.entity.ProductEntity;
import com.projectbase.entity.ShoppingSessionEntity;
import com.projectbase.entity.UserEntity;
import com.projectbase.exception.ApplicationException;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.EntityStatus;
import com.projectbase.mapper.ShoppingCartMapper;
import com.projectbase.model.CartItem;
import com.projectbase.model.ShoppingCart;
import com.projectbase.repository.CartItemRepository;
import com.projectbase.repository.ProductRepository;
import com.projectbase.repository.ShoppingSessionRepository;
import com.projectbase.repository.UserRepository;
import com.projectbase.service.ShoppingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShoppingServiceImpl implements ShoppingService{

    @Autowired
    private ShoppingSessionRepository shoppingSessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public ShoppingCart createShoppingSession(Long userId){

        Optional<UserEntity> otpCustomer = userRepository.findById(userId);
        if(!otpCustomer.isPresent()){
            log.error("The customer has id {} not found", userId);
            throw new ValidationException("Customer not found");
        }

        UserEntity customer = otpCustomer.get();
        if(customer.getStatus() != EntityStatus.ACTIVATED) {
            log.error("The customer has id {} has been deactivated or can be pending", userId);
            throw new ValidationException("the customer has been de-activated");
        }

        ShoppingSessionEntity shoppingSession = shoppingSessionRepository.findByCustomerId(customer.getId());
        if(shoppingSession != null){
            return shoppingCartMapper.fromShoppingCartEntity(shoppingSession);
        }

        shoppingSession = ShoppingSessionEntity.builder()
                .customer(customer)
                .build();

        shoppingSession = shoppingSessionRepository.save(shoppingSession);

        return shoppingCartMapper.fromShoppingCartEntity(shoppingSession);
    }

    @Override
    public ShoppingCart findById(Long cartId){
        Optional<ShoppingSessionEntity> otpShoppingSession = shoppingSessionRepository.findById(cartId);

        return otpShoppingSession
                .map(shoppingSession -> shoppingCartMapper.fromShoppingCartEntity(shoppingSession))
                .orElse(null);
    }

    @Override
    @Transactional
    public Boolean deleteById(Long cartId){
        try{
            shoppingSessionRepository.deleteById(cartId);
            return Boolean.TRUE;
        }catch(Exception e){
            log.error("Cannot delete the cart {}", cartId);
            return Boolean.FALSE;
        }
    }

    @Override
    @Transactional
    public ShoppingCart addItemToCart(Long cartId, CartItem cartItem){

        Optional<ShoppingSessionEntity> optionalShoppingSession = shoppingSessionRepository.findById(cartId);

        // check to see if the cart not exist
        if(!optionalShoppingSession.isPresent()){
            log.error("Shopping cart has id {} not found", cartId);
            throw new ApplicationException("Shopping cart is invalid");
        }

        Optional<ProductEntity> otpProductEntity = productRepository.findById(cartItem.getProduct().getId());
        // check to see if the product not exist
        if(!otpProductEntity.isPresent()){
            throw new ApplicationException("Product not found");
        }

        ProductEntity productEntity = otpProductEntity.get();
        // check to see if the amount of product is not enough
        if(productEntity.getAmount() - cartItem.getQuantity() <= 0){
            throw new ApplicationException(String.format("Only %s items left", productEntity.getAmount()));
        }

        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .quantity(cartItem.getQuantity())
                .product(productEntity)
                .build();

        ShoppingSessionEntity shoppingSession = optionalShoppingSession.get();
        Set<CartItemEntity> cartItemEntities = shoppingSession.getCartItems();
        if(cartItemEntities == null){
            cartItemEntities = new HashSet<>();
        }
        cartItemEntities.add(cartItemEntity);

        // calculate the total of bill
        BigDecimal total = cartItemEntities.stream()
                .map(c -> c.getProduct().getPrice().multiply(BigDecimal.valueOf(c.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        shoppingSession.setTotal(total);

        shoppingSession = shoppingSessionRepository.save(shoppingSession);

        return shoppingCartMapper.fromShoppingCartEntity(shoppingSession);
    }
}
