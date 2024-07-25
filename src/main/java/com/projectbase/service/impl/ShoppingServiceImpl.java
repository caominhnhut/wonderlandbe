package com.projectbase.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectbase.entity.ShoppingSessionEntity;
import com.projectbase.entity.UserEntity;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.EntityStatus;
import com.projectbase.mapper.ShoppingCartMapper;
import com.projectbase.model.ShoppingCart;
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

    @Override
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
}
