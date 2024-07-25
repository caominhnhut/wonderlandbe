package com.projectbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projectbase.entity.ShoppingSessionEntity;

public interface ShoppingSessionRepository extends JpaRepository<ShoppingSessionEntity, Long>{

    @Query("SELECT sh FROM ShoppingSessionEntity sh WHERE sh.customer.id = :customerId")
    ShoppingSessionEntity findByCustomerId(@Param("customerId") Long customerId);

}
