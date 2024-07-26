package com.projectbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectbase.entity.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long>{

}
