package com.projectbase.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projectbase.dto.ChangePasswordRequestDto;
import com.projectbase.dto.ForgotPasswordRequestDto;
import com.projectbase.dto.ResponseDto;
import com.projectbase.dto.UserRequest;
import com.projectbase.dto.UserResponse;
import com.projectbase.factory.UserRoleName;
import com.projectbase.factory.UserStatus;
import com.projectbase.factory.ValidationType;
import com.projectbase.mapper.UserMapper;
import com.projectbase.model.User;
import com.projectbase.service.UserService;
import com.projectbase.validator.ValidatorProvider;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ValidatorProvider validatorProvider;

    @PostMapping
    public ResponseEntity<ResponseDto<Long>> userRegister(@RequestBody UserRequest userRequest) {

        log.info("Register an user with data: {}", userRequest);

        validatorProvider.execute(ValidationType.USER_CREATION, userRequest);

        User user = userMapper.toUser(userRequest);
        user.setRoles(Set.of(UserRoleName.CUSTOMER.toString()));
        User newlyUser = userService.create(user);
        return ResponseEntity.ok(ResponseDto.response(newlyUser.getId()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<UserResponse>>> findAll() {
        List<User> users = userService.findAll();
        List<UserResponse> userResponses = users.stream().map(user -> userMapper.fromUser(user)).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseDto.response(userResponses));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/status/active")
    public ResponseEntity<ResponseDto<List<UserResponse>>> findByStatus() {
        List<User> users = userService.findByStatus(UserStatus.ACTIVATED);
        List<UserResponse> userResponses = users.stream().map(user -> userMapper.fromUser(user)).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseDto.response(userResponses));
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UserResponse>> findById(@PathVariable Long id) {
        Optional<User> optUser = userService.findById(id);
        if(optUser.isPresent()){
            UserResponse userResponses = userMapper.fromUser(optUser.get());
            return ResponseEntity.ok(ResponseDto.response(userResponses));
        }
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Boolean>> update(@PathVariable Long id,  @RequestBody UserRequest userRequest) {

        validatorProvider.execute(ValidationType.USER_UPDATING, userRequest);

        User user = userMapper.toUser(userRequest);
        user.setId(id);
        boolean isUpdated = userService.update(user);

        return ResponseEntity.ok(ResponseDto.response(isUpdated));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseDto<Boolean>> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto) {

        validatorProvider.execute(ValidationType.USER_CHANGE_PASSWORD, changePasswordRequestDto);

        boolean isUpdated = userService.changePassword(changePasswordRequestDto);

        return ResponseEntity.ok(ResponseDto.response(isUpdated));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDto<String>> recoverPassword(@RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) {

        validatorProvider.execute(ValidationType.FORGOT_PASSWORD, forgotPasswordRequestDto);

        userService.recoverPassword(forgotPasswordRequestDto);

        return ResponseEntity.ok(ResponseDto.response("The password has been sent to your email"));
    }
}
