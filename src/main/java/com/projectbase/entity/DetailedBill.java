package com.projectbase.entity;

import lombok.Data;

@Data
public class DetailedBill{

    private String name;
    private String category;
    private String quantity;
    private String price;
    private String total;
}
