package com.projectbase.service;

import java.util.List;
import java.util.Optional;

import com.projectbase.dto.ChangePasswordRequestDto;
import com.projectbase.dto.ForgotPasswordRequestDto;
import com.projectbase.factory.UserStatus;
import com.projectbase.model.Category;
import com.projectbase.model.User;

public interface CategoryService{

    Long create(Category category);

    List<Category> findAll();

    void update(Category category);
}
