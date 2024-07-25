package com.projectbase.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "order_item")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemEntity extends CustomBaseEntity implements Serializable{

    @Column(name = "quantity")
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrdersEntity order;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;
}
