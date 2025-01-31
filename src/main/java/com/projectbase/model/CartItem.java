package com.projectbase.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItem extends CustomBaseModel implements Serializable{

    private Integer quantity;

    private Product product;
}
