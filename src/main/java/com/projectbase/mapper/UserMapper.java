package com.projectbase.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.projectbase.dto.UserRequest;
import com.projectbase.dto.UserResponse;
import com.projectbase.entity.RoleEntity;
import com.projectbase.entity.UserEntity;
import com.projectbase.model.User;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper{

    @Mapping(target = "roles", ignore = true)
    UserEntity toUserEntity(User user);

    @Mapping(target = "roles", expression = "java(getRolesFromUserEntity(user.getRoles()))")
    User fromUserEntity(UserEntity user);

    User toUser(UserRequest userRequest);

    @Mapping(target = "roles", source = "user.roles")
    UserResponse fromUser(User user);

    default Set<String> getRolesFromUserEntity(Set<RoleEntity> roles) {
       return roles.stream().map(RoleEntity::getName).collect(Collectors.toSet());
    }
}
