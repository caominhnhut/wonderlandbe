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
@Table(name = "shopping_session")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ShoppingSessionEntity extends CustomBaseEntity implements Serializable{

    @Column(name = "total")
    private BigDecimal total;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private UserEntity customer;

    @OneToMany(mappedBy = "shoppingSession")
    private Set<CartItemEntity> cartItems;
}
