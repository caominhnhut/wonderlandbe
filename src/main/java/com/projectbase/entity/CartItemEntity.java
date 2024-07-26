package com.projectbase.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cart_item")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartItemEntity extends CustomBaseEntity implements Serializable{

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private ShoppingSessionEntity shoppingSession;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;
}
