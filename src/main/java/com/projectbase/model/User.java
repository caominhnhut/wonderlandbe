package com.projectbase.model;

import java.util.Set;

import com.projectbase.factory.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User{

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private UserStatus status;

    private Set<String> roles;
}
