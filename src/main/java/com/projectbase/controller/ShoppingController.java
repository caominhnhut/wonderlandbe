package com.projectbase.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projectbase.dto.ResponseDto;
import com.projectbase.exception.ApplicationException;
import com.projectbase.model.ShoppingCart;
import com.projectbase.model.User;
import com.projectbase.service.ShoppingService;
import com.projectbase.service.UserService;
import com.projectbase.service.impl.AuthenticationService;
import com.projectbase.service.impl.UserDetailsServiceImpl;

@RestController
@RequestMapping(value ="/")
public class ShoppingController{

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/shopping-cart")
    public ResponseEntity<ResponseDto<Long>> createShoppingSession(){

        String email = authenticationService.getCurrentLoggedInUser();

        Optional<User> optUser = userService.findByEmail(email);

        Long userId = optUser.orElseThrow(()->new ApplicationException("Unauthorized")).getId();

        ShoppingCart shoppingCart = shoppingService.createShoppingSession(userId);

        return ResponseEntity.ok(ResponseDto.response(shoppingCart.getId()));
    }
}
