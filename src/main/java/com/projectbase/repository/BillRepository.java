package com.projectbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectbase.entity.BillEntity;

public interface BillRepository extends JpaRepository<BillEntity, Long>{

}
