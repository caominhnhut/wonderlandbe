package com.projectbase.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ShoppingCart extends CustomBaseModel implements Serializable{

    private BigDecimal total;

    private Long customerId;

    private List<CartItem> cartItems;

}
