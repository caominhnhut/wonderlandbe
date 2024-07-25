package com.projectbase.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "orders")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class OrdersEntity extends CustomBaseEntity implements Serializable{

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "order_status")
    private String orderStatus;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    @OneToMany(mappedBy = "order")
    private Set<OrderItemEntity> orderItems;

    @OneToOne(mappedBy = "order")
    private PaymentEntity payment;
}
