package com.projectbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectbase.entity.DocumentEntity;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long>{
//
//    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
//    UserEntity findByEmail(@Param("email") String email);
//
//    @Query("SELECT u FROM UserEntity u WHERE u.status = :status")
//    List<UserEntity> findByStatus(@Param("status") EntityStatus status);
//
//    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.status = :status")
//    UserEntity findByEmailAndStatus(@Param("email") String email, @Param("status") EntityStatus status);
}
