package com.projectbase.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectbase.config.security.JwtRequestFilter;
import com.projectbase.dto.ChangePasswordRequestDto;
import com.projectbase.dto.ForgotPasswordRequestDto;
import com.projectbase.entity.RoleEntity;
import com.projectbase.entity.UserEntity;
import com.projectbase.exception.ValidationException;
import com.projectbase.factory.MessageSendingType;
import com.projectbase.factory.UserStatus;
import com.projectbase.mapper.UserMapper;
import com.projectbase.model.MessageContent;
import com.projectbase.model.User;
import com.projectbase.repository.RoleRepository;
import com.projectbase.repository.UserRepository;
import com.projectbase.service.UserService;
import com.projectbase.service.messaging.MessageService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final MessageService messageService;

    private final JwtRequestFilter jwtRequestFilter;

    @Override
    @Transactional
    public User create(User user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        UserEntity userEntity = userMapper.toUserEntity(user);

        userEntity.setPassword(encryptedPassword);
        userEntity.setStatus(UserStatus.ACTIVATED);

        Set<RoleEntity> roles = roleRepository.findByNames(user.getRoles());
        userEntity.setRoles(roles);

        UserEntity createdUserEntity = userRepository.save(userEntity);

        MessageContent messageContent = MessageContent.builder()
                .from("nguyencaominhnhut@gmail.com")
                .to(user.getEmail())
                .subject("Welcome New Account")
                .content("Welcome. How are you")
                .build();

        messageService.sendMessage(messageContent, MessageSendingType.EMAIL);

        return userMapper.fromUserEntity(createdUserEntity);
    }

    @Override
    public List<User> findAll(){
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(userMapper::fromUserEntity).collect(Collectors.toList());
    }

    @Override
    public List<User> findByStatus(UserStatus status){
        List<UserEntity> userEntities = userRepository.findByStatus(status);
        return userEntities.stream().map(userMapper::fromUserEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id){
        Optional<UserEntity> optEntity = userRepository.findById(id);
        if(!optEntity.isPresent()){
            return Optional.empty();
        }

        User user = userMapper.fromUserEntity(optEntity.get());
        return Optional.of(user);
    }

    @Override
    @Transactional
    public boolean update(User user){

        log.info("Updating user has id {}", user.getId());

        Optional<UserEntity> optEntity = userRepository.findById(user.getId());
        if(!optEntity.isPresent()){
            throw new ValidationException(String.format("Not found user has id %s", user.getId()));
        }

        UserEntity userEntity = optEntity.get();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        userRepository.save(userEntity);

        return  true;
    }

    @Override
    @Transactional
    public boolean changePassword(ChangePasswordRequestDto changePasswordRequestDto){
        UserEntity userEntity = userRepository.findByEmail(jwtRequestFilter.getCurrentUser());
        if(null == userEntity){
            throw new ValidationException("User not found");
        }

        if(!passwordEncoder.matches(changePasswordRequestDto.getOldPassword(), userEntity.getPassword())){
            throw new ValidationException("The old password is incorrect");
        }

        String encryptedNewPassword = passwordEncoder.encode(changePasswordRequestDto.getNewPassword());

        userEntity.setPassword(encryptedNewPassword);
        userRepository.save(userEntity);

        return true;
    }

    @Override
    public void recoverPassword(ForgotPasswordRequestDto forgotPasswordRequestDto){

        UserEntity userEntity = userRepository.findByEmail(forgotPasswordRequestDto.getEmailAddress());
        if(null == userEntity){
            throw new ValidationException("Email is invalid");
        }

        MessageContent messageContent = MessageContent.builder()
                .from("nguyencaominhnhut@gmail.com")
                .to(forgotPasswordRequestDto.getEmailAddress())
                .subject("CoffeeShop Recovery Password")
                .content("Click here to reset your password")
                .build();

        messageService.sendMessage(messageContent, MessageSendingType.EMAIL);
    }
}
