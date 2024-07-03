package com.projectbase.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest{

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String confirmedPassword;

    private List<String> roles;
}
