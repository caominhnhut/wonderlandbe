package com.projectbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projectbase.entity.UserEntity;
import com.projectbase.exception.AuthenticationException;
import com.projectbase.factory.EntityStatus;
import com.projectbase.model.MyUserDetail;
import com.projectbase.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        UserEntity userEntity = userRepository.findByEmailAndStatus(username, EntityStatus.ACTIVATED);

        if(userEntity == null){
            log.error("{} not found", username);
            throw new AuthenticationException("username is not valid");
        }

        return new MyUserDetail(userEntity);
    }
}