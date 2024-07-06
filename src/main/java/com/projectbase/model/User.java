package com.projectbase.model;

import java.util.Set;

import com.projectbase.factory.EntityStatus;

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

    private EntityStatus status;

    private Set<String> roles;
}
