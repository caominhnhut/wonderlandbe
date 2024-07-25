package com.projectbase.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService{

    public String getCurrentLoggedInUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
