package com.projectbase.service;

import java.util.List;
import java.util.Optional;

import com.projectbase.dto.ChangePasswordRequestDto;
import com.projectbase.dto.ForgotPasswordRequestDto;
import com.projectbase.factory.UserStatus;
import com.projectbase.model.User;

public interface UserService{

    User create(User user);

    List<User> findAll();

    List<User> findByStatus(UserStatus status);

    Optional<User> findById(Long id);

    boolean update(User user);

    boolean changePassword(ChangePasswordRequestDto changePasswordRequestDto);

    void recoverPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);
}
