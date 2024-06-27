package com.projectbase.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projectbase.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

    @Query("SELECT c FROM CategoryEntity c WHERE c.id in :ids")
    Set<CategoryEntity> findByIds(@Param("ids") Set<Long> ids);

}
