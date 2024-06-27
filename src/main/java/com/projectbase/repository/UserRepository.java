package com.projectbase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projectbase.entity.UserEntity;
import com.projectbase.factory.UserStatus;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    UserEntity findByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u WHERE u.status = :status")
    List<UserEntity> findByStatus(@Param("status") UserStatus status);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.status = :status")
    UserEntity findByEmailAndStatus(@Param("email") String email, @Param("status") UserStatus status);
}
