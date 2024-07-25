package com.projectbase.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "payment")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class PaymentEntity extends CustomBaseEntity implements Serializable{

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_status")
    private String paymentStatus;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrdersEntity order;
}
