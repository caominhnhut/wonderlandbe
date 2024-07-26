package com.projectbase.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectbase.dto.CartItemDto;
import com.projectbase.dto.ResponseDto;
import com.projectbase.exception.ApplicationException;
import com.projectbase.mapper.ShoppingCartMapper;
import com.projectbase.model.CartItem;
import com.projectbase.model.ShoppingCart;
import com.projectbase.model.User;
import com.projectbase.service.ShoppingService;
import com.projectbase.service.UserService;
import com.projectbase.service.impl.AuthenticationService;

@RestController
@RequestMapping(value ="/")
public class ShoppingController{

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @PostMapping("/shopping-cart")
    public ResponseEntity<ResponseDto<Long>> createShoppingSession(){

        String email = authenticationService.getCurrentLoggedInUser();

        Optional<User> optUser = userService.findByEmail(email);

        Long userId = optUser.orElseThrow(()->new ApplicationException("Unauthorized")).getId();

        ShoppingCart shoppingCart = shoppingService.createShoppingSession(userId);

        return ResponseEntity.ok(ResponseDto.response(shoppingCart.getId()));
    }

    @GetMapping("/shopping-cart/{cart-id}")
    public ResponseEntity<ResponseDto<ShoppingCart>> getCartById(@PathVariable("cart-id") Long cartId){

        ShoppingCart shoppingCart = shoppingService.findById(cartId);

        return ResponseEntity.ok(ResponseDto.response(shoppingCart));
    }

    @DeleteMapping("/shopping-cart/{cart-id}")
    public ResponseEntity<ResponseDto<Boolean>> deleteCartById(@PathVariable("cart-id") Long cartId){
        Boolean result = shoppingService.deleteById(cartId);
        return ResponseEntity.ok(ResponseDto.response(result));
    }

    @PutMapping("/shopping-cart/{cart-id}")
    public ResponseEntity<ResponseDto<ShoppingCart>> addItemToCart(@PathVariable("cart-id") Long cartId, @RequestBody CartItemDto cartItemDto){

        CartItem cartItem = shoppingCartMapper.toCartItem(cartItemDto);
        ShoppingCart shoppingCart = shoppingService.addItemToCart(cartId, cartItem);

        return ResponseEntity.ok(ResponseDto.response(shoppingCart));
    }
}
